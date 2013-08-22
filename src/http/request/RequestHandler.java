package http.request;

import http.EmptyRequestException;
import http.server.socket.WebSocket;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class RequestHandler {
  private String NEW_LINE = "/r/n";
  private String request;
  private ByteBuffer bodyBuffer;

  public String receive(WebSocket webSocket) throws IOException, EmptyRequestException {
    receiveHeader(webSocket);
    handleBody(webSocket);
    return request;
  }

  private void receiveHeader(WebSocket webSocket) throws IOException, EmptyRequestException {
    try {
      String line;
      StringBuffer buffer = new StringBuffer();

      while ( !(line = webSocket.in().readLine() ).equals("") ) {
        buffer.append(line);
        buffer.append("\r\n");
      }
      request = buffer.toString();
    } catch (NullPointerException e) {
      throw new EmptyRequestException();
    }
  }

  public void handleBody(WebSocket webSocket) throws IOException {
    if (request.startsWith("POST") || request.startsWith("PUT")) {
      request += NEW_LINE;
      receiveBody(webSocket);
    }
  }

  public void receiveBody(WebSocket webSocket) throws IOException {
    readToBuffer(webSocket);
    transferToRequest();
  }

  private void readToBuffer(WebSocket webSocket) throws IOException {
    bodyBuffer = ByteBuffer.allocate(requestContentLength());
    int remainingChrs;

    while ( bodyBuffer.remaining() > 0 ) {
      remainingChrs = webSocket.in().read();
      bodyBuffer.put( (byte) remainingChrs);
    }
  }

  private void transferToRequest() throws UnsupportedEncodingException {
    bodyBuffer.position(0);
    byte[] bufferInBytes = new byte[bodyBuffer.remaining()];
    bodyBuffer.get(bufferInBytes);

    request += new String(bufferInBytes, "UTF-8");
  }

  public int requestContentLength() {
    String[] requestArray = request.split(NEW_LINE);
    String[] contentLengthArray = new String[3];

    for(int i=0; i<requestArray.length; i++) {
      if (requestArray[i].startsWith("Content-Length"))
        contentLengthArray = requestArray[i].split(" ");
    }
    return Integer.parseInt(contentLengthArray[1]);
  }
}