package http;

import java.util.Arrays;

public class Server {
  private Request request;
  private Router router;
  private Header header;
  private Body body;
  private WebServerSocket theServerSocket;

  public Server(int port, String publicDirectory) throws Exception {
    request = new Request();
    header = new Header();
    body = new Body();
    this.router = new Router(publicDirectory, new DefaultHashMap(Router.NOT_FOUND));

    ServerSocketFactory theServerSocketFactory = new ServerSocketFactory();
    theServerSocket = theServerSocketFactory.get("production", port);
    System.out.println("HTTP Server is running on port " + port + ".");

    while (true) {
      theServerSocket.connect();
      RequestHandler requestHandler = new RequestHandler(theServerSocket);
      String receivedRequest = requestHandler.receive();
      System.out.println("receivedRequest = " + receivedRequest);
      request.parseHeader(receivedRequest);
      String route = router.get(request.baseURL);
      byte[] responseBody = body.get(route, request.queryString);
      byte[] responseHeader = header.get(route, request.httpMethod, responseBody.length);
      requestHandler.sendResponse(responseHeader);
      requestHandler.sendResponse(responseBody);
      theServerSocket.closeConnection();
    }
  }

  public static void main(String[] args) throws Exception {
      int portIndex = Arrays.asList(args).indexOf("-p");
      Integer port = Integer.parseInt(args[portIndex+1]);

      int publicDirectoryIndex = Arrays.asList(args).indexOf("-d");
      String publicDirectory = args[publicDirectoryIndex+1];

      new Server(port, publicDirectory);
    }
}