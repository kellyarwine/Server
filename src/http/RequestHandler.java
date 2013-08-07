package http;

import java.io.IOException;

public class RequestHandler {
  public WebServerSocket theServerSocket;

  public RequestHandler(WebServerSocket theServerSocket) throws IOException {
    this.theServerSocket = theServerSocket;
  }

  public String receive() throws IOException {
    int chr;
    StringBuffer buffer = new StringBuffer();
    while ( (chr = theServerSocket.in().read()) != -1) {
      buffer.append((char) chr);
      if (!theServerSocket.in().ready()) break;
    }
    return buffer.toString();
  }

  public void sendResponse(byte[] content) throws IOException {
    theServerSocket.out().write(content);
    theServerSocket.out().flush();
  }
}