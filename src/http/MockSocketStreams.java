package http;

import java.io.*;
import java.net.Socket;

public class MockSocketStreams implements WebSocketStreams {
  private InputStream data;
  private OutputStream output;

  public MockSocketStreams(String dataString) {
    byte[] dataBytes = dataString.getBytes();
    data = new ByteArrayInputStream(dataBytes);

    output = new ByteArrayOutputStream();
  }

  @Override
  public Socket listen() {
    null
  }

  @Override
  public InputStream in() {
    return data;
  }

  @Override
  public OutputStream out() {
    return output;
  }

}
