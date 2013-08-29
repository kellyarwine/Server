package http.server.serverSocket;

import http.server.socket.SystemSocket;
import http.server.socket.WebSocket;

import java.io.IOException;
import java.net.ServerSocket;

public class SystemHttpServerSocket implements HttpServerSocket {
  ServerSocket serverSocket;

  public SystemHttpServerSocket(int port) throws IOException {
    serverSocket = new ServerSocket(port);
  }
  public WebSocket accept() throws IOException {
    return new SystemSocket(serverSocket.accept());
  }

  public void close() throws IOException {
    serverSocket.close();
  }

  public boolean isClosed() {
    return serverSocket.isClosed();
  }

  public boolean isBound() {
    return serverSocket.isBound();
  }

}
