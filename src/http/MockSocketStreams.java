package http;

import java.io.*;

public class MockSocketStreams implements WebSocketStreams {
  private InputStream input;
  private OutputStream output;

  public MockSocketStreams(String dataString) {
    byte[] dataBytes = dataString.getBytes();
    input = new BufferedInputStream(new ByteArrayInputStream(dataBytes));

    output = new BufferedOutputStream(new ByteArrayOutputStream());
  }

  @Override
  public InputStream in() {
    return input;
  }

  @Override
  public OutputStream out() {
    return output;
  }

}
