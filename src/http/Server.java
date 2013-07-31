package http;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Server {
  private int port;
  private String publicDirectory;
  private String env;
  private String workingDirectory;
  private File publicDirectoryFullPath;

  private Router router;
  private Request request;
  private ResponseHeader responseHeader;
  private ResponseBody responseBody;
  private SystemLogger logger;
  private WebServerSocket theServerSocket;

  public Server(int port, String publicDirectory, String env) throws Exception {
    this.port = port;
    this.publicDirectory = publicDirectory;
    this.env = env;
    this.workingDirectory = System.getProperty("user.dir");
    this.publicDirectoryFullPath = new File(workingDirectory, publicDirectory);
    this.router = new Router(this.publicDirectory, new DefaultHashMap(Router.NOT_FOUND));
    request = new Request();
    responseHeader = new ResponseHeader();
    responseBody = new ResponseBody(publicDirectoryFullPath);
    logger = new ConsoleLogger();
    run();
  }

  public void run() throws IOException {
    ServerSocketFactory theServerSocketFactory = new ServerSocketFactory();
    WebServerSocket theServerSocket = theServerSocketFactory.get(port, env);

    logger.out("HTTP SERVER IS RUNNING.");
    logger.out("PORT: " + port);
    logger.out("ENVIRONMENT: " + env);

    while (true) {
      theServerSocket.connect();
      RequestHandler requestHandler = new RequestHandler(theServerSocket);
      String receivedRequest = requestHandler.receive();
      logger.out("REQUEST: " + receivedRequest);
      request.parseMessage(receivedRequest);
      File routeFile = router.get(request.baseURL);
      logger.out("RENDERED: " + routeFile);
      byte[] responseBodyBytes = responseBody.get(routeFile, request.queryString);
      byte[] responseHeaderBytes = responseHeader.get(routeFile, request.httpMethod, responseBodyBytes.length);
      requestHandler.sendResponse(responseHeaderBytes);
      requestHandler.sendResponse(responseBodyBytes);
      theServerSocket.closeConnection();
    }
  }

  public static void main(String[] args) throws Exception {
    int portIndex = Arrays.asList(args).indexOf("-p");
    Integer port = Integer.parseInt(args[portIndex+1]);

    int publicDirectoryIndex = Arrays.asList(args).indexOf("-d");
    String publicDirectory = args[publicDirectoryIndex+1];

//    int envIndex = Arrays.asList(args).indexOf("-e");
//    String env = args[envIndex+1];

    new Server(port, publicDirectory, "production");
  }
}