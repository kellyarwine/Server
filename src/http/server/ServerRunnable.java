package http.server;

import http.request.QueryStringRepository;
import http.server.logger.Logger;
import http.server.logger.LoggerFactory;
import http.server.serverSocket.HttpServerSocket;
import http.server.serverSocket.ServerSocketFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerRunnable implements Runnable {
  private HashMap<String, String> serverConfig;
  private File publicDirectoryFullPath;
  private Logger logger;
  private QueryStringRepository queryStringRepository;
  public HttpServerSocket httpServerSocket;
  public volatile boolean closeRequested;
  public volatile boolean exceptionThrown;

  public ServerRunnable(HashMap<String, String> serverConfig) throws IOException, URISyntaxException {
    this.serverConfig = serverConfig;
    File workingDirectoryPath = new File(serverConfig.get("workingDirectoryPath"));
    this.publicDirectoryFullPath = new File(workingDirectoryPath, serverConfig.get("publicDirectoryPath"));
    this.logger = new LoggerFactory().build(serverConfig.get("env"), workingDirectoryPath);
    queryStringRepository = new QueryStringRepository();
    copyTemplatesToDisk();
    closeRequested = false;
    exceptionThrown = false;
  }

  public void run() {
    try {
      httpServerSocket = new ServerSocketFactory().build(serverConfig);

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
    } catch (Exception e) {
      exceptionThrown = true; }
  }

  private void copyTemplatesToDisk() throws IOException, URISyntaxException {
    new Templater().copyTemplatesToDisk("/http/templates/templates.zip", publicDirectoryFullPath);
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