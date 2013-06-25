package http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface WebSocketStreams {
  InputStream  in() throws IOException;
  OutputStream out() throws IOException;
}
