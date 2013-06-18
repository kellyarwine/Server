package http;

import java.io.IOException;

public class HTTPBrowser {

  public String publicDirectory;
  public WebSocketStreams streams;

  public HTTPBrowser(WebSocketStreams streams, String publicDirectory) throws IOException {
    this.streams = streams;
  }

  public String receiveRequest() throws IOException {
    StringBuffer buffer = new StringBuffer(100);

    while (true) {
      int chr = streams.in().read();
      if (chr == -1 || chr == '\r' || chr == '\n') break;
      buffer.append( (char) chr );
    }

    return buffer.toString();
  }

  public String sendResponse() {
    return "okay";
  }

}