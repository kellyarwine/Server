package http;

import java.io.*;

public class MockServerSocket implements WebServerSocket {
  private String dataString;
  public InputStream in;
  public OutputStream out;

  public MockServerSocket(String dataString) {
    this.dataString = dataString;
  }

  public void connect() {
    byte[] dataBytes = dataString.getBytes();
    in = new BufferedInputStream(new ByteArrayInputStream(dataBytes));
    out = new ByteArrayOutputStream();
  }

  public InputStream in() {
    return in;
  }

  public OutputStream out() {
    return out;
  }

  public void closeConnection() {
  }

}
