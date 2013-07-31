package http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class RequestHandler {
  public String receivedRequest;
  public WebServerSocket theServerSocket;
  private String NEWLINE = "\r\n";
  private ByteBuffer bodyBuffer;

  public RequestHandler(WebServerSocket theServerSocket) throws IOException {
    this.theServerSocket = theServerSocket;
  }

  public String receive() throws  IOException {
    receiveHeader();
    handleBody();
    return receivedRequest;
  }

  public void receiveHeader() throws IOException {
    StringBuffer buffer = new StringBuffer(100);
    String line;

    while ( !(line = theServerSocket.in().readLine()).equals("") ) {
        buffer.append(line);
        buffer.append("\r\n");
    }

    receivedRequest = buffer.toString();
  }

  public void handleBody() throws IOException {
    if (receivedRequest.startsWith("POST")) {
      receivedRequest += NEWLINE;
      receiveBody();
    }
  }

  public void receiveBody() throws IOException {
    readRequestBodyToBuffer();
    readBufferToReceivedRequest();
  }

  private void readBufferToReceivedRequest() throws UnsupportedEncodingException {
    bodyBuffer.position(0);
    byte[] bufferInBytes = new byte[bodyBuffer.remaining()];
    bodyBuffer.get(bufferInBytes);

    receivedRequest += new String(bufferInBytes, "UTF-8");
  }

  private void readRequestBodyToBuffer() throws IOException {
    bodyBuffer = ByteBuffer.allocate(requestContentLength());
    int remainingChrs;

    while ( bodyBuffer.remaining() > 0 ) {
      remainingChrs = theServerSocket.in().read();
      bodyBuffer.put( (byte) remainingChrs);
    }
  }

  public void sendResponse(byte[] content) throws IOException {
    theServerSocket.out().write(content);
    theServerSocket.out().flush();
  }

  public int requestContentLength() {
    String[] requestArray = receivedRequest.split(NEWLINE);
    String[] contentLengthArray = new String[3];

    for(int i=0; i<requestArray.length; i++) {
      if (requestArray[i].startsWith("Content-Length"))
        contentLengthArray = requestArray[i].split(" ");
    }
    return Integer.parseInt(contentLengthArray[1]);
  }
}