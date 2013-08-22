package http.server.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface WebSocket {
  InputStream in()       throws IOException;
  OutputStream   out()      throws IOException;
  void           close()    throws IOException;
  boolean        isClosed() throws IOException;
}