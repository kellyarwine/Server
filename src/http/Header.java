package http;

import java.util.Date;

public class Header {

  public byte[] get(String baseURL, String requestedHTTPMethod, int contentLengthOfURL) {
    String stringHeader = build(baseURL, requestedHTTPMethod, contentLengthOfURL);
    return stringHeader.getBytes();
    }

  public String build(String baseURL, String requestedHTTPMethod, int contentLengthOfURL) {
    String stringHeader = httpProtocolVersion() + " " + httpMethod(requestedHTTPMethod, baseURL)
        + "\r\n" + currentDate()
        + "\r\n" + serverInfo()
        + "\r\n" + contentType(baseURL)
        + "\r\n" + contentLength(contentLengthOfURL)
        + "\r\n\r\n";
    return stringHeader;
  }

  public String httpProtocolVersion() {
    return "HTTP/1.1";
  }

  public String httpMethod(String requestedHTTPMethod, String baseURL) {
    String method = null;
    if (baseURL.endsWith("/404.html")) method = "404 File Not Found";
    else if (requestedHTTPMethod.equals("GET")) method = "200 OK";

    return method;
  }

  public Date currentDate() {
    return new Date();
  }

  public String serverInfo() {
    return "Server: NinjaServer 1.0";
  }

  public String contentType(String baseURL) {
    String contentTypeOfURL = "text/plain; charset=UTF-8";

    if (baseURL.endsWith(".html") || baseURL.endsWith(".htm")) contentTypeOfURL = "text/html; charset=UTF-8";
    if (baseURL.endsWith(".jpg") || baseURL.endsWith(".jpeg")) contentTypeOfURL = "image/jpeg";
    if (baseURL.endsWith(".gif")) contentTypeOfURL = "image/gif";
    if (baseURL.endsWith(".png")) contentTypeOfURL = "image/png";

    return "Content-type: " + contentTypeOfURL;
  }

  public String contentLength(int contentLengthOfURL) {
    return "Content-length: " + contentLengthOfURL;
  }

}