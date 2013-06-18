package http;

import java.util.Date;

public class Header {

  public String get(String baseURL, String requestedHTTPMethod, int contentLengthOfURL) {
    return  httpProtocolVersion() + " " + httpMethod(requestedHTTPMethod, baseURL)
            + "\n" + currentDate()
            + "\n" + serverInfo()
            + "\n" + contentType(baseURL)
            + "\n" + contentLength(contentLengthOfURL);
    }

  public String httpProtocolVersion() {
    return "HTTP/1.1";
  }

  public String httpMethod(String requestedHTTPMethod, String baseURL) {
    String method = null;

    if (baseURL.endsWith("/404.html")) method = "404 File Not Found";
    else if (requestedHTTPMethod == "GET") method = "200 OK";

    return method;
  }

  public Date currentDate() {
    return new Date();
  }

  public String serverInfo() {
    return "http.Server: NinjaServer 1.0";
  }

  public String contentType(String baseURL) {
    String contentTypeOfURL = "text/plain; charset=UTF-8";

    if (baseURL.endsWith(".html") || baseURL.endsWith(".htm")) contentTypeOfURL = "text/html; charset=UTF-8";
    if (baseURL.endsWith(".jpg") || baseURL.endsWith(".jpeg")) contentTypeOfURL = "image/jpeg";
    if (baseURL.endsWith(".gif")) contentTypeOfURL = "image/gif";
    if (baseURL.endsWith(".png")) contentTypeOfURL = "image/png";

    return "Content-Type: " + contentTypeOfURL;
  }

  public String contentLength(int contentLengthOfURL) {
    return "Content-length: " + contentLengthOfURL + "\n";
  }

}