package http.server;

import http.request.QueryStringRepository;
import http.request.Request;
import http.response.Response;
import http.server.logger.Logger;
import http.server.socket.WebSocket;
import router.Router;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServerRequestThread implements Runnable {
  public WebSocket webSocket;
  private Logger logger;
  private String publicDirectoryPath;
  private String routesFilePath;
  private String htAccessFilePath;
  private File workingDirectory;
  private QueryStringRepository queryStringRepository;

  public ServerRequestThread(Map serverConfig, Logger logger, WebSocket webSocket, QueryStringRepository queryStringRepository){
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
      byte[] builtResponse = router.getResponse(receivedRequest);
      String route = router.getRoute(receivedRequest);
      generateLogMessages(receivedRequest, route);
      response.send(webSocket.out(), builtResponse);

      webSocket.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void generateLogMessages(HashMap receivedRequest, String route) throws IOException {
    String host = (String)receivedRequest.get("Host");
    String url = (String)receivedRequest.get("url");
    logger.logMessage(" REQUEST: http://" + host  + url);
    logger.logMessage("RENDERED: http://" + host + getRelativeRoute(route));
  }

  private String getRelativeRoute(String route) {
    String publicDirectoryFullPath = new File(workingDirectory, publicDirectoryPath).toString();
    return route.replace(publicDirectoryFullPath, "");
  }
}