import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Date;

public class HTTPBrowser {
  private ServerSocket theServerSocket;
  private String directory;

  OutputStream out;
  InputStream in;

  public String HTTPRequest;
  public String route;
  public String HTTPVersion;
  public String queryString;
  public String[] queryArray;

  public String request;

  private String twoHundredStatusCode;
  private String fourOhFourStatusCode;


  public HTTPBrowser(ServerSocket theServerSocket, String directory) {
    this.theServerSocket = theServerSocket;
    this.directory = directory;
    twoHundredStatusCode = "200 OK";
    fourOhFourStatusCode = "404 File Not Found";
  }

  public void openSocket() throws Exception {
    while (true) {
      try {
        Socket theConnection = theServerSocket.accept();
        in = new BufferedInputStream(theConnection.getInputStream());
        out = new BufferedOutputStream(theConnection.getOutputStream());
        getRequest();
        sendResponse();
      }
      catch (IOException e) {
        System.err.println(e);
      }
    }
  }

  public void getRequest() throws IOException {
    readRequest();
    parseRequest();
  }

  private void readRequest() throws IOException {
    StringBuffer requestStringBuffer = new StringBuffer(100);
    while (true) {
      int c = in.read();
      if (c == '\r' || c == '\n' || c == -1) break;
      requestStringBuffer.append( (char) c );
      request = requestStringBuffer.toString();
    }
  }

  public void parseRequest() {
    String[] requestArray = request.split(" ");
    HTTPRequest = requestArray[0];
    route = directory + requestArray[1];
    HTTPVersion = requestArray[2];
  }

  public String[] parseRoute(String route) {
    return route.split("\\?");
  }

  public String[] parseQueryString(String queryString) {
    return queryString.split("=");
  }

  public void sendResponse() throws Exception {
    if (HTTPRequest.equals("GET")) {
      getResponse();
    }
    else if (HTTPRequest.equals("PUT")) {

    }
    else if (HTTPRequest.equals("POST")) {

    }
    else if (HTTPRequest.equals("DELETE")) {

    }
    else {
      System.out.println("You have no valid HTTP method.");
    }
  }

  public void getResponse() throws Exception {
    String[] routeParts = parseRoute(route);
    File theFile = new File(routeParts[0]);
    if (theFile.canRead()) {

      if (routeParts.length > 1) {
        String[] queryStringArray = parseQueryString(routeParts[1]);
        out.write(header( twoHundredStatusCode, bodyWithQueryString(routeParts[0], queryStringArray).length, contentType(routeParts[0])) );
        System.out.print(Arrays.toString(queryStringArray));
        out.write(bodyWithQueryString(routeParts[0], queryStringArray));
      }
      else {
        out.write(header( twoHundredStatusCode, body(routeParts[0]).length, contentType(routeParts[0])) );
        out.write(body(routeParts[0]));
      }

      out.flush();
    }
    else {
      out.write(header(fourOhFourStatusCode, body(directory + "/404.html").length, contentType(routeParts[0])));
      out.write(body(directory + "/404.html"));
      out.flush();
    }
  }

  private byte[] header(String responseCode, int bodyLength, String contentType) throws IOException {
    String data =  "HTTP/1.1 " + responseCode + "\r\n"
        + "Date: " + new Date() + "\r\n"
        + "Server: NinjaServer 1.0\r\n"
        + "Host: localhost:5000\r\n"
        + "Content-length: " + bodyLength + "\r\n"
        + "Connection: close\r\n"
        + "Content-type: " + contentType + "\r\n\r\n";
    return data.getBytes("ASCII");
  }

  private byte[] body(String route) throws IOException {
    InputStream fileIn = new FileInputStream(route);
    ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
    int b;

    while ( ( b = fileIn.read() ) != -1 ) fileOut.write(b);
    return fileOut.toByteArray();
  }

  private byte[] bodyWithQueryString(String route, String[] queryArray)
    throws Exception {

      BufferedReader reader = new BufferedReader(new FileReader(route));
      ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
      PrintWriter writer = new PrintWriter(fileOut);
      String line;

      while ((line = reader.readLine()) != null) writer.println(line.replaceAll(queryArray[0],queryArray[1]));

      writer.close();
      reader.close();
      return fileOut.toByteArray();
  }

  private String contentType(String route) {
      String contentType = "text/plain; charset = UTF-8";

      if (route.endsWith(".html") || route.endsWith(".htm")) contentType = "text/html; charset = ISO-8859-1";
      if (route.endsWith(".jpg") || route.endsWith(".jpeg")) contentType = "image/jpeg";
      if (route.endsWith(".gif")) contentType = "image/gif";
      if (route.endsWith(".png")) contentType = "image/png";

      return contentType;
  }
}