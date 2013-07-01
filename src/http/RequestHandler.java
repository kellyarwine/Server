package http;

import java.io.IOException;

public class RequestHandler {

  public WebServerSocket theServerSocket;

  public RequestHandler(WebServerSocket theServerSocket) throws IOException {
    this.theServerSocket = theServerSocket;
  }

  public String receiveRequest() throws IOException {
    StringBuffer buffer = new StringBuffer(100);
    int chr;

    while ( (chr = theServerSocket.in().read()) != -1 && chr != '\r' && chr != '\n' ) {
      buffer.append( (char) chr );
    }

    return buffer.toString();
  }

  public void sendResponse(byte[] content) throws IOException {
    theServerSocket.out().write(content);
    theServerSocket.out().flush();
  }

}