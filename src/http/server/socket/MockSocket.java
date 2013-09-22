package http.server.socket;

import java.io.*;

public class MockSocket implements WebSocket {
  private BufferedReader in;
  private OutputStream out;
  public boolean isClosed;

  public MockSocket(String requestString) throws IOException {
    byte[] request = requestString.getBytes();
    in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(request)));
    out = new ByteArrayOutputStream();
    isClosed = false;
  }

  public BufferedReader in() {
    return in;
  }

  public OutputStream out() {
    return out;
  }

  public void close() throws IOException {
    isClosed = true;
  }

  public boolean getIsClosed() {
    return isClosed;
  }
}