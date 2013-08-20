package http.response.code;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class TwoHundredSix extends Code {
  private File routeFile;
  private int startRange;
  private int endRange;
  private int fileSize;

  public byte[] build(File routeFile, HashMap request) throws IOException {
    this.routeFile = routeFile;
    this.request = request;
    assignRangeParts((String)request.get("Range"));
    byte[] responseBody = buildBody(routeFile);
    byte[] responseHeader = buildHeader(routeFile, responseCodeMessage(), buildBody(routeFile).length);
    byte[] newLineInBytes = NEW_LINE.getBytes();
    return concatenate(new byte[][] {responseHeader, newLineInBytes, responseBody});
  }

  public byte[] buildHeader(File routeFile, String responseCodeMessage, int bodyContentLength) throws IOException {
    byte[] contentRange = ("Content-Range: bytes " + startRange + "-" + endRange + "/" + fileSize + "\r\n").getBytes();
    byte[] originalHeader = new ResponseHeader().build(routeFile, responseCodeMessage(), bodyContentLength);
    return concatenate(new byte[][] {originalHeader, contentRange});
  }

  public byte[] buildBody(File routeFile) throws IOException {
    byte[] originalBody = super.buildBody(routeFile);
    byte[] trimmedBody = new byte[endRange - startRange];
    System.arraycopy(originalBody, startRange, trimmedBody, 0, endRange - startRange);
    return trimmedBody;
  }

  public String responseCodeMessage() {
    return "206 Partial Content";
  }

  private void assignRangeParts(String range) throws IOException {
    String actualRange = getActualRange(range);
    getStartRange(actualRange);
    getEndRange(actualRange);
    getFileSize();
  }

  private String getActualRange(String range) {
    return range.split("=")[1];
  }

  private void getStartRange(String actualRange) {
    int index = actualRange.indexOf("-");
    startRange = Integer.parseInt(actualRange.substring(0, index));
  }

  private void getEndRange(String actualRange) {
    int index = actualRange.indexOf("-");
    endRange = Integer.parseInt(actualRange.substring(index+1));
  }

  private void getFileSize() throws IOException {
    fileSize = super.buildBody(routeFile).length;
  }
}