package http.server;

import http.request.QueryStringRepository;
import http.request.Request;
import http.response.Response;
import http.router.Router;
import http.server.logger.SystemLogger;
import http.server.socket.WebSocket;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerRequestThread implements Runnable {
  public WebSocket webSocket;
  private SystemLogger logger;
  private String publicDirectoryPath;
  private String routesFilePath;
  private String htAccessFilePath;
  private File workingDirectory;
  private QueryStringRepository queryStringRepository;

  public ServerRequestThread(Map serverConfig, SystemLogger logger, WebSocket webSocket, QueryStringRepository queryStringRepository){
    this.webSocket = webSocket;
    this.logger = logger;
    this.publicDirectoryPath = (String)serverConfig.get("publicDirectoryPath");
    this.routesFilePath = (String)serverConfig.get("routesFilePath");
    this.htAccessFilePath = (String)serverConfig.get("htAccessFilePath");
    this.workingDirectory = new File((String)serverConfig.get("workingDirectoryPath"));
    this.queryStringRepository = queryStringRepository;
  }

  public void run() {
    try {
      Request request = new Request(queryStringRepository);
      Router router = new Router(workingDirectory, publicDirectoryPath, routesFilePath, htAccessFilePath);
      Response response = new Response();

      HashMap receivedRequest = request.get(webSocket);
      logger.logMessage(" REQUEST: http://" + receivedRequest.get("Host") + receivedRequest.get("url"));

      ArrayList routeInfo = router.getRouteInfo(receivedRequest);

      response.send(webSocket.out(), receivedRequest, routeInfo);
      File routeFile = (File)routeInfo.get(0);
      String routeFilePath = routeFile.getAbsolutePath();
      logger.logMessage("RENDERED: http://" + receivedRequest.get("Host") + subtractPath(routeFilePath, new File(workingDirectory, publicDirectoryPath)));

      webSocket.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String subtractPath(String routeFilePath, File publicDirectoryFullPath) {
    return routeFilePath.replace(publicDirectoryFullPath.toString(), "");
  }
}