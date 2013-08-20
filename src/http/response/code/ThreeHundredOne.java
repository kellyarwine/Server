package http.response.code;

import java.io.File;
import java.io.IOException;

public class ThreeHundredOne extends Code {
  public byte[] buildHeader(File routeFile, String responseCodeMessage, int bodyContentLength) throws IOException {
    byte[] originalHeader = new ResponseHeader().build(routeFile, responseCodeMessage, bodyContentLength);
    byte[] location = ("Location: " + toURL(routeFile) + "\r\n").getBytes();
    return concatenate(new byte[][] {originalHeader, location});
  }

  public byte[] buildBody(File routeFile) {
    return "".getBytes();
  }

  public String responseCodeMessage() {
    return "301 Moved Permanently";
  }

  private String toURL(File routeFile) {
    return "http://" + request.get("Host") + "/" + routeFile.getName();
  }
}