package http;

import java.io.IOException;

public class RequestHandler {
  public String receivedRequest;
  public WebServerSocket theServerSocket;

  public RequestHandler(WebServerSocket theServerSocket) throws IOException {
    this.theServerSocket = theServerSocket;
  }

  public String receive() throws  IOException {
    receiveHeader();
//    System.out.println("receivedHeader = " + receivedRequest);
    handleBody();
//    System.out.println("receivedBody = " + receivedRequest);

    return receivedRequest;
  }

  public void receiveHeader() throws IOException {
    StringBuffer buffer = new StringBuffer(100);
    String chrs;

    while ( !(chrs = theServerSocket.in().readLine()).equals("") ) {
        buffer.append(chrs);
        buffer.append("\r\n");
    }

    receivedRequest = buffer.toString();
  }

  public void handleBody() throws IOException {
    if (receivedRequest.startsWith("POST")) {
      receiveBody();
    }
  }

  public void receiveBody() throws IOException {
    int contentLength = getContentLength();
    StringBuffer buffer = new StringBuffer(contentLength);
    String remainingChrs = "";

    buffer.append("\r\n");

    while ( buffer.length() < contentLength ) {
      remainingChrs = theServerSocket.in().readLine();

      System.out.println("Did I get here?");

      buffer.append(remainingChrs);
      buffer.append("\r\n");
    }

    receivedRequest += buffer.toString();
  }

  public void sendResponse(byte[] content) throws IOException {
    theServerSocket.out().write(content);
    theServerSocket.out().flush();
  }

  public int getContentLength() {
    String[] requestArray = receivedRequest.split("\r\n");
    String[] contentLengthArray = new String[3];

    for(int i=0; i<requestArray.length; i++) {
      if (requestArray[i].startsWith("Content-Length"))
        contentLengthArray = requestArray[i].split(" ");
    }
    return Integer.parseInt(contentLengthArray[1]);
  }

}