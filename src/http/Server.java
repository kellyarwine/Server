package http;

import java.net.ServerSocket;
import java.util.Arrays;

public class Server {
  private int port;
  private String publicDirectory;
  private String environment;
  private WebSocketStreams streams;

  private Request request = new Request();
  private Router router = new Router();
  private Header header = new Header();
  private Body body = new Body();

  public Server(int port, String publicDirectory) throws Exception {
    this.port = port;
    this.publicDirectory = publicDirectory;
    this.environment = "production";

    ServerSocket theServerSocket = new ServerSocket(port);
    System.out.println("HTTP Server is running on port " + port + ".");

    if (environment == "production")
      streams = new SystemSocketStreams(theServerSocket);
    else
      streams = new MockSocketStreams("This is some text.");

    HTTPBrowser browser = new HTTPBrowser(streams, publicDirectory);

    while (true) {
      streams.listen();
      String receivedRequest = browser.receiveRequest();
      System.out.print("\n");
      System.out.print("receivedRequest is: " + receivedRequest);

      request.parse(receivedRequest);
      String route = router.get(request.baseURL, publicDirectory);
      String responseBody = body.get(route, request.queryString);
      String responseHeader = header.get(route, request.httpMethod, body.contentLengthInBytes(responseBody));
      String content = responseHeader + "\r\n\r\n" + responseBody;
      browser.sendResponse(content);
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