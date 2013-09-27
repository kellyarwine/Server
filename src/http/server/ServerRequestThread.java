package http.server;

import http.SystemRouter;
import http.request.QueryStringRepository;
import http.request.Request;
import http.response.Response;
import http.server.logger.Logger;
import http.server.socket.WebSocket;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

public class ServerRequestThread implements Runnable {
  public WebSocket webSocket;
  private Logger logger;
  private File workingDirectory;
  private String publicDirectoryPath;
  private SystemRouter router;
  private Request request;
  private Response response;

  public ServerRequestThread(HashMap<String, String> serverConfig, Logger logger, WebSocket webSocket, QueryStringRepository queryStringRepository, SystemRouter router) throws IOException {
    this.webSocket = webSocket;
    this.logger = logger;
    this.publicDirectoryPath = serverConfig.get("publicDirectoryPath");
    this.workingDirectory = new File(serverConfig.get("workingDirectoryPath"));
    request = new Request(queryStringRepository);
    this.router = router;
    response = new Response();
  }

  public void run() {
    try {
      HashMap receivedRequest = request.get(webSocket);
      byte[] builtResponse = router.getResponse(receivedRequest);
      String route = router.getRoute(receivedRequest);
      generateLogMessages(receivedRequest, route);
      response.send(webSocket.out(), builtResponse);

      webSocket.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    catch (ParseException e) {
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