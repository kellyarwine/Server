package http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MockSocketStreams implements WebSocketStreams {
  private InputStream data;
  private OutputStream output;

  public MockSocketStreams(String dataString) {
    byte[] dataBytes = dataString.getBytes();
    data = new ByteArrayInputStream(dataBytes);

    output = new ByteArrayOutputStream();
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
