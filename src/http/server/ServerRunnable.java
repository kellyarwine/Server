package http.server;

import http.request.QueryStringRepository;
import http.router.Templater;
import http.server.logger.Logger;
import http.server.logger.LoggerFactory;
import http.server.serverSocket.HttpServerSocket;
import http.server.serverSocket.SystemHttpServerSocket;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerRunnable implements Runnable {
  private Map<String, String> serverConfig;
  private File publicDirectoryFullPath;
  private Logger logger;
  private QueryStringRepository queryStringRepository;
  public HttpServerSocket httpServerSocket;
  private volatile boolean closeRequested;

  public ServerRunnable(Map<String, String> serverConfig) throws IOException, URISyntaxException {
    this.serverConfig = serverConfig;
    File workingDirectoryPath = new File(serverConfig.get("workingDirectoryPath"));
    this.publicDirectoryFullPath = new File(workingDirectoryPath, serverConfig.get("publicDirectoryPath"));
    this.logger = new LoggerFactory().build(serverConfig.get("env"), workingDirectoryPath);
    queryStringRepository = new QueryStringRepository();
    copyTemplatesToDisk();
    closeRequested = false;
  }

  public void run() {
    try {
      int port = Integer.parseInt(serverConfig.get("port"));
      httpServerSocket = new SystemHttpServerSocket(port);

      logger.logMessage("Ninja Server is running in " + serverConfig.get("env").toString() + " mode on port " + serverConfig.get("port") + ".  WOOT!");
      logger.logMessage("Now serving files from: " + serverConfig.get("publicDirectoryPath"));
      logger.logMessage("Working directory: " + serverConfig.get("workingDirectoryPath"));
      logger.logMessage("Routes Filename: " + serverConfig.get("routesFilePath"));
      logger.logMessage(".htaccess Filename: " + serverConfig.get("htAccessFilePath") + "\n");

      int cores = Runtime.getRuntime().availableProcessors();
      ExecutorService serverRequestThreadPool = Executors.newFixedThreadPool(cores);

      while (!closeRequested) {
        ServerRequestThread serverRequestThread = new ServerRequestThread(serverConfig, logger, httpServerSocket.accept() , queryStringRepository);
        serverRequestThreadPool.submit(serverRequestThread);
      }
    }
    catch (IOException e) {
    }
  }

  private void copyTemplatesToDisk() throws IOException, URISyntaxException {
    new Templater().copyTemplatesToDisk("/http/templates/", publicDirectoryFullPath);
  }

  public void closeServerSocket() throws IOException {
    closeRequested = true;
    httpServerSocket.close();
    deleteDirectory(new File(publicDirectoryFullPath, "/templates"));
  }

  public void deleteDirectory(File directory)
  {
    if (directory.isDirectory()) {
      String[] children = directory.list();
      for (int i=0; i<children.length; i++) {
        deleteDirectory(new File(directory, children[i]));
      }
    }
    directory.delete();
  }
}