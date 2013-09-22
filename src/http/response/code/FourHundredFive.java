package http.response.code;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class FourHundredFive extends Code {
  public byte[] buildHeader(File routeFile, String responseCodeMessage, int bodyContentLength) throws IOException, ParseException {
    byte[] originalHeader = new ResponseHeader().build(routeFile, responseCodeMessage(), buildBody(routeFile).length);
    byte[] allowableMethods = ("Allow: GET" + "\r\n").getBytes();
    return concatenate(new byte[][] {originalHeader, allowableMethods});
  }

  public byte[] buildBody(File routeFile) {
    return "".getBytes();
  }

  public String responseCodeMessage() {
    return "405 Method Not Allowed";
  }
}
