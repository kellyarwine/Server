package http.server.socket;

import java.io.*;

public class MockSocket implements WebSocket {
  private InputStream in;
  private OutputStream out;

  public MockSocket(String requestString) {
    byte[] request = requestString.getBytes();
    in = new ByteArrayInputStream(request);
    out = new ByteArrayOutputStream();
  }

  public InputStream in() {
    return in;
  }

  public OutputStream out() {
    return out;
  }

  public void close() throws IOException {
    in.close();
    out.close();
  }

  public boolean isClosed() {
    return false;
  }
}