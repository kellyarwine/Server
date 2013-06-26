package http;

import java.util.Date;

public class Header {

  private String baseURL;
  private String requestedHTTPMethod;
  private int contentLengthOfURL;

  public byte[] get(String baseURL, String requestedHTTPMethod, int contentLengthOfURL) {
    this.baseURL = baseURL;
    this.requestedHTTPMethod = requestedHTTPMethod;
    this.contentLengthOfURL = contentLengthOfURL;
    return build().getBytes();
    }

  public String build() {
    String stringHeader = httpProtocolVersion() + " " + httpMethod()
        + "\r\n" + currentDate()
        + "\r\n" + serverInfo()
        + "\r\n" + contentType()
        + "\r\n" + contentLength()
        + "\r\n\r\n";
    return stringHeader;
  }

  public String httpProtocolVersion() {
    return "HTTP/1.1";
  }

  public String httpMethod() {
    String method = null;

    if (baseURL.endsWith("/404.html")) method = "404 File Not Found";
    else if (requestedHTTPMethod.equals("GET")) method = "200 OK";
    else if (requestedHTTPMethod.equals("POST")) method = "something";

    return method;
  }

  public Date currentDate() {
    return new Date();
  }

  public String serverInfo() {
    return "Server: NinjaServer 1.0";
  }

  public String contentType() {
    String contentTypeOfURL = "text/plain; charset=UTF-8";

    if (baseURL.endsWith(".html") || baseURL.endsWith(".htm")) contentTypeOfURL = "text/html; charset=UTF-8";
    if (baseURL.endsWith(".jpg") || baseURL.endsWith(".jpeg")) contentTypeOfURL = "image/jpeg";
    if (baseURL.endsWith(".gif")) contentTypeOfURL = "image/gif";
    if (baseURL.endsWith(".png")) contentTypeOfURL = "image/png";

    return "Content-type: " + contentTypeOfURL;
  }

  public String contentLength() {
    return "Content-length: " + contentLengthOfURL;
  }

}