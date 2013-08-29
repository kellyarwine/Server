package http.server.serverSocket;

import http.server.socket.WebSocket;

import java.io.IOException;

public interface HttpServerSocket {
  WebSocket accept()   throws IOException;
  void      close()    throws IOException;
  boolean   isClosed() throws IOException;
  boolean   isBound()  throws IOException;
}
