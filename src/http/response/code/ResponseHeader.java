package http.response.code;

import java.io.File;
import java.util.Date;

public class ResponseHeader {
  public byte[] build(File routeFile, String responseCodeMessage, int bodyContentLength) {
    String header = httpProtocolVersion() + " " + responseCodeMessage + "\r\n"
                        + currentDate()+ "\r\n"
                        + serverInfo()+ "\r\n"
                        + contentType(routeFile)+ "\r\n"
                        + contentLength(bodyContentLength)+ "\r\n";
    return header.getBytes();
  }

  private String httpProtocolVersion() {
    return "HTTP/1.1";
  }

  private Date currentDate() {
    return new Date();
  }

  private String serverInfo() {
    return "Server: NinjaServer 1.0";
  }

  private String contentType(File routeFile) {
    String contentTypeOfURL;

    if (match(routeFile, "jpg") || match(routeFile, "jpeg")) contentTypeOfURL = "image/jpeg";
    else if (match(routeFile, "gif")) contentTypeOfURL = "image/gif";
    else if (match(routeFile, "png")) contentTypeOfURL = "image/png";
    else if (match(routeFile, "txt")) contentTypeOfURL = "text/plain; charset=UTF-8";
    else contentTypeOfURL = "text/html; charset=UTF-8";

    return "Content-type: " + contentTypeOfURL;
  }

  private boolean match(File routeFile, String extensionToMatch) {
    return routeFile.toString().endsWith(extensionToMatch);
  }

  private String contentLength(int bodyContentLength) {
    return "Content-length: " + bodyContentLength;
  }
}