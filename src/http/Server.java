package http;

import java.net.ServerSocket;
import java.util.Arrays;

public class Server {
  private int port;
  private String publicDirectory;
  private String environment;
  private WebSocketStreams streams;

  private Request request = new Request();
  private Router router;
  private Header header = new Header();
  private Body body = new Body();

  public Server(int port, String publicDirectory) throws Exception {
    this.port = port;
    this.publicDirectory = publicDirectory;
    this.environment = "production";
    this.router = new Router(publicDirectory, new DefaultHashMap(Router.NOT_FOUND));
    ServerSocket theServerSocket = new ServerSocket(port);
    System.out.println("HTTP Server is running on port " + port + ".");

//    streams = new SystemSocketStreams(theServerSocket);

    while (true) {
//      streams = new SystemSocketStreams(theServerSocket);
//      HTTPBrowser browser = new HTTPBrowser(streams, publicDirectory);

      if (environment == "production")
        streams = new SystemSocketStreams(theServerSocket);
      else
        streams = new MockSocketStreams("This is some text.");

      HTTPBrowser browser = new HTTPBrowser(streams, publicDirectory);

      String receivedRequest = browser.receiveRequest();
      request.parse(receivedRequest);
      String route = router.get(request.baseURL);
      byte[] responseBody = body.get(route, request.queryString);
      byte[] responseHeader = header.get(route, request.httpMethod, responseBody.length);
      browser.sendResponse(responseHeader);
      browser.sendResponse(responseBody);
      // get remaining parameters
    }
  }

  public static void main(String[] args) throws Exception {
      int portIndex = Arrays.asList(args).indexOf("-p");
      Integer port = Integer.parseInt(args[portIndex+1]);

      int publicDirectoryIndex = Arrays.asList(args).indexOf("-d");
      String publicDirectory = args[publicDirectoryIndex+1];

      Server theServer = new Server(port, publicDirectory);
    }
}