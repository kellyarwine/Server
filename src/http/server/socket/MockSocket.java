package http.server.socket;

import java.io.*;

public class MockSocket implements WebSocket {
  private BufferedReader in;
  private OutputStream out;

  public MockSocket(String requestString) {
    byte[] request = requestString.getBytes();
    in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(request)));
    out = new ByteArrayOutputStream();
  }

  public BufferedReader in() {
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