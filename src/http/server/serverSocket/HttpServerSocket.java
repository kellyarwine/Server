package http.server.serverSocket;

import http.server.socket.WebSocket;

import java.io.IOException;

public interface HttpServerSocket {
  WebSocket accept()     throws IOException;
}
