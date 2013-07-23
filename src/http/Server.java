package http;

import java.io.IOException;

public class Server {
  private Request request;
  private Router router;
  private Header header;
  private Body body;
  private WebServerSocket theServerSocket;
  private int port;

  public Server(int port, String publicDirectory) throws Exception {
    request = new Request();
    header = new Header();
    body = new Body();
    this.port = port;
    this.router = new Router(publicDirectory, new DefaultHashMap(Router.NOT_FOUND));
  }

  public void run() throws IOException {
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

}