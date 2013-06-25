package http;

import java.net.ServerSocket;
import java.util.Arrays;
import java.util.HashMap;

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
    this.router = new Router(publicDirectory, new HashMap());
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

      System.out.print("\n");
      System.out.print("receivedRequest is: " + receivedRequest);
      System.out.print("\n");

      request.parse(receivedRequest);
      String route = router.get(request.baseURL);
      byte[] responseBody = body.get(route, request.queryString);
      System.out.print("\nThis is query string:");
      System.out.print(request.queryString);
      System.out.print("\n");
      byte[] responseHeader = header.get(route, request.httpMethod, responseBody.length);
      String content = responseHeader + "\r\n\r\n" + responseBody;
      System.out.print(responseHeader);
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