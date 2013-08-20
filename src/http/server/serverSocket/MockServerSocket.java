package http.server.serverSocket;

import java.io.*;

public class MockServerSocket implements WebServerSocket {
  private String dataString;
  public BufferedReader in;
  public OutputStream out;

  public MockServerSocket(String dataString) {
    this.dataString = dataString;
  }

  public void connect() {
    byte[] dataBytes = dataString.getBytes();
    in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(dataBytes)));
    out = new ByteArrayOutputStream();
  }

  public BufferedReader in() {
    return in;
  }

  public OutputStream out() {
    return out;
  }

  public void closeConnection() {
  }

}
