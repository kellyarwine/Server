package http;

import java.util.Date;

public class Header {

  public String get(String URL, String requestedHTTPMethod, boolean URLExists, int contentLengthOfURL) {
    return  httpProtocolVersion() + " " + httpMethod(requestedHTTPMethod, URLExists)
            + "\n" + currentDate()
            + "\n" + serverInfo()
            + "\n" + contentType(URL)
            + "\n" + contentLength(contentLengthOfURL);
    }

  public String httpProtocolVersion() {
    return "HTTP/1.1";
  }

  public String httpMethod(String requestedHTTPMethod, boolean URLExists) {
    String method = null;

    if (URLExists == false) method = "404 File Not Found";
    else if (requestedHTTPMethod == "GET") method = "200 OK";

    return method;
  }

  public Date currentDate() {
    return new Date();
  }

  public String serverInfo() {
    return "Server: NinjaServer 1.0";
  }

  public String contentType(String URL) {
    String contentTypeOfURL = "text/plain; charset = UTF-8";

    if (URL.endsWith(".html") || URL.endsWith(".htm")) contentTypeOfURL = "text/html; charset = ISO-8859-1";
    if (URL.endsWith(".jpg") || URL.endsWith(".jpeg")) contentTypeOfURL = "image/jpeg";
    if (URL.endsWith(".gif")) contentTypeOfURL = "image/gif";
    if (URL.endsWith(".png")) contentTypeOfURL = "image/png";

    return "Content-Type: " + contentTypeOfURL;
  }

  public String contentLength(int contentLengthOfURL) {
    return "Content-length: " + contentLengthOfURL + "\n";
  }

}