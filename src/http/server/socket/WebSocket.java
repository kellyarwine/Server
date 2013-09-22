package http.server.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

public interface WebSocket {
  boolean        getIsClosed();
  BufferedReader in()       throws IOException;
  OutputStream   out()      throws IOException;
  void           close()    throws IOException;
}