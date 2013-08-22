package http.server.serverSocket;

import http.server.socket.WebSocket;

import java.io.IOException;

public interface WebServerSocket {
  WebSocket accept()     throws IOException;
}
