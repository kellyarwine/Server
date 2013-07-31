package http;

import java.io.File;
import java.util.Date;

public class ResponseHeader {
  private File routeFile;
  private String requestedHTTPMethod;
  private int contentLengthOfURL;

  public byte[] get(File routeFile, String requestedHTTPMethod, int contentLengthOfURL) {
    this.routeFile = routeFile;
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

    if (routeFile.getName().equals("404.html")) method = "404 File Not Found";
    else if (requestedHTTPMethod.equals("GET")) method = "200 OK";

    return method;
  }

  public Date currentDate() {
    return new Date();
  }

  public String serverInfo() {
    return "Server: NinjaServer 1.0";
  }

  public String contentType() {
    String contentTypeOfURL;

    if (matchesFileExtension(routeFile, "jpg") || matchesFileExtension(routeFile, "jpeg")) contentTypeOfURL = "image/jpeg";
    else if (matchesFileExtension(routeFile, "gif")) contentTypeOfURL = "image/gif";
    else if (matchesFileExtension(routeFile, "png")) contentTypeOfURL = "image/png";
    else if (matchesFileExtension(routeFile, "txt")) contentTypeOfURL = "text/plain; charset=UTF-8";
    else contentTypeOfURL = "text/html; charset=UTF-8";

    return "Content-type: " + contentTypeOfURL;
  }

  private boolean matchesFileExtension(File routeFile, String extensionToMatch) {
    return routeFile.toString().endsWith(extensionToMatch);
  }

  public String contentLength() {
    return "Content-length: " + contentLengthOfURL;
  }
}