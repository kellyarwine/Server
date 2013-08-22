package http.server;

import http.EmptyRequestException;
import http.request.Request;
import http.response.Response;
import http.router.Router;
import http.server.logger.SystemLogger;
import http.server.socket.WebSocket;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerThread extends Thread {
  private WebSocket webSocket = null;
  private String publicDirectory = null;
  private String routesFilePath = null;
  private String htAccessFilePath = null;
  private SystemLogger logger = null;
  private File workingDirectory = null;

  public ServerThread(WebSocket webSocket, SystemLogger logger, String publicDirectory, String routesFilePath, String htAccessFilePath, File workingDirectory) {
    super("ServerThread");
    this.webSocket = webSocket;
    this.logger = logger;
    this.publicDirectory = publicDirectory;
    this.routesFilePath = routesFilePath;
    this.htAccessFilePath = htAccessFilePath;
    this.workingDirectory = workingDirectory;
  }

  public void run() {
    try {
      Request request = new Request();
      Router router = new Router(workingDirectory, publicDirectory, routesFilePath, htAccessFilePath);
      Response response = new Response();

      HashMap receivedRequest = request.get(webSocket);
      logger.logMessage("REQUEST: http://" + receivedRequest.get("Host") + receivedRequest.get("url"));

      ArrayList routeInfo = router.getRouteInfo(receivedRequest);

      response.send(webSocket.out(), receivedRequest, routeInfo);
      File routeFile = (File)routeInfo.get(0);
      String routeFilePath = routeFile.getAbsolutePath();
      logger.logMessage("RENDERED: http://" + receivedRequest.get("Host") + "/" + subtractPath(routeFilePath, new File(workingDirectory + publicDirectory)));

      webSocket.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    catch (EmptyRequestException e) {
      System.err.println("No request received.");;
    }
  }

  private String subtractPath(String routeFilePath, File publicDirectoryFullPath) {
    return routeFilePath.replace(publicDirectoryFullPath.toString(), "");
  }
}