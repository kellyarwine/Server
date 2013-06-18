//package http;
//
//import java.io.*;
//import java.net.Socket;
//import java.util.Date;
//
//public class HTTPBrowser {
//  private WebSocketStreams theServerSocket;
//  private String directory;
//  OutputStream out;
//  InputStream in;
//  private String twoHundredStatusCode;
//  private String fourOhFourStatusCode;
//
//  public String HTTPRequest;
//  public String route;
//  public String HTTPVersion;
//
//
//  public HTTPBrowser(WebSocketStreams theServerSocket, String directory) {
//    this.theServerSocket = theServerSocket;
//    this.directory = directory;
//    twoHundredStatusCode = "200 OK";
//    fourOhFourStatusCode = "404 File Not Found";
//  }
//
//  public void openSocket() throws Exception {
//    while (true) {
//        Socket theConnection = theServerSocket.accept();
//        in = new BufferedInputStream(theConnection.getInputStream());
//        out = new BufferedOutputStream(theConnection.getOutputStream());
//        String request = readRequest();
//        parseRequest(request);
//        sendResponse();
//    }
//  }
//
//  private String readRequest() throws IOException {
//    StringBuffer requestStringBuffer = new StringBuffer(100);
//    while (true) {
//      int c = in.read();
//      if (c == -1 || c == '\r' || c == '\n') break;
//      requestStringBuffer.append( (char) c );
//    }
//    return requestStringBuffer.toString();
//  }
//
//  public void parseRequest(String request) {
//    String[] requestArray = request.split(" ");
//    HTTPRequest = requestArray[0];
//    route = directory + requestArray[1];
//    HTTPVersion = requestArray[2];
//  }
//
//  public String[] parseRoute(String route) {
//    return route.split("\\?");
//  }
//
//  public String[] parseQueryString(String queryString) {
//    return queryString.split("=");
//  }
//
//  public void sendResponse() throws Exception {
//    if (HTTPRequest.equals("GET"))
//      getResponse();
//    else if (HTTPRequest.equals("POST"))
//      postResponse();
//    else
//      System.out.println("You do not have a valid HTTP method.");
//  }
//
//  public void getResponse() throws Exception {
//    String[] routeParts = parseRoute(route);
//    File theFile = new File(routeParts[0]);
//
//    if (theFile.canRead()) {
//      handleRoute(routeParts);
//    }
//    else {
//      out.write(header(fourOhFourStatusCode, body(directory + "/404.html").length, contentType(routeParts[0]) ));
//      out.write(body(directory + "/404.html"));
//    }
//
//    out.flush();
//  }
//
//  public void handleRoute(String[] routeParts) throws Exception {
//    if (routeParts.length > 1) {
//      String[] queryStringArray = parseQueryString(routeParts[1]);
//      out.write(header( twoHundredStatusCode, bodyWithQueryString(routeParts[0], queryStringArray).length, contentType(routeParts[0])) );
//      out.write(bodyWithQueryString(routeParts[0], queryStringArray));
//    }
//    else {
//      out.write(header( twoHundredStatusCode, body(routeParts[0]).length, contentType(routeParts[0])) );
//      out.write(body(routeParts[0]));
//    }
//  }
//
//  private byte[] header(String responseCode, int bodyLength, String contentType) throws IOException {
//    String data =  "HTTP/1.1 " + responseCode + "\r\n"
//        + "Date: " + new Date() + "\r\n"
//        + "Server: NinjaServer 1.0\r\n"
//        + "Host: localhost:5000\r\n"
//        + "Content-length: " + bodyLength + "\r\n"
//        + "Connection: close\r\n"
//        + "Content-type: " + contentType + "\r\n\r\n";
//    return data.getBytes("ASCII");
//  }
//
//  private String contentType(String route) {
//    String contentType = "text/plain; charset = UTF-8";
//
//    if (route.endsWith(".html") || route.endsWith(".htm")) contentType = "text/html; charset = ISO-8859-1";
//    if (route.endsWith(".jpg") || route.endsWith(".jpeg")) contentType = "image/jpeg";
//    if (route.endsWith(".gif")) contentType = "image/gif";
//    if (route.endsWith(".png")) contentType = "image/png";
//
//    return contentType;
//  }
//
//  private byte[] body(String route) throws IOException {
//    InputStream fileIn = new FileInputStream(route);
//    ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
//    int b;
//
//    while ( ( b = fileIn.read() ) != -1 ) fileOut.write(b);
//    return fileOut.toByteArray();
//  }
//
//  private byte[] bodyWithQueryString(String route, String[] queryArray)
//    throws Exception {
//
//      BufferedReader reader = new BufferedReader(new FileReader(route));
//      ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
//      PrintWriter writer = new PrintWriter(fileOut);
//      String line;
//
//      while ((line = reader.readLine()) != null) writer.println(line.replaceAll(queryArray[0],queryArray[1]));
//
//      writer.close();
//      reader.close();
//      return fileOut.toByteArray();
//  }
//
//  public void postResponse() throws Exception {
//  }
//
//}