package http.response.code;

import java.io.*;
import java.util.HashMap;

public class Code {
  public String NEW_LINE = "\r\n";
  public HashMap request;

  public byte[] build(File routeFile, HashMap request) throws IOException {
    this.request = request;
    byte[] responseBody = buildBody(routeFile);
    byte[] responseHeader = buildHeader(routeFile, responseCodeMessage(), buildBody(routeFile).length);
    byte[] newLineInBytes = NEW_LINE.getBytes();
    return concatenate(new byte[][] {responseHeader, newLineInBytes, responseBody});
  }

  public byte[] buildBody(File routeFile) throws IOException {
    byte[] responseBody = new ResponseBody().build(routeFile);
    byte[] updatedResponseBody = new QueryString().updateBody(responseBody, request);
    return updatedResponseBody;
  }

  public byte[] buildHeader(File routeFile, String responseCodeMessage, int bodyContentLength) throws IOException {
    return new ResponseHeader().build(routeFile, responseCodeMessage, bodyContentLength);
  }

//  This could be accomplished by avoiding an output stream, but I wanted the httpMethod to accept a variable number of byte arrays.
  public byte[] concatenate(byte[][] byteArray) throws IOException {
    ByteArrayOutputStream bOutput = new ByteArrayOutputStream();
    for(int i=0; i<byteArray.length; i++) {
      bOutput.write(byteArray[i]);
    }
    return bOutput.toByteArray();
  }

  public String responseCodeMessage() {
    return "";
  }
}