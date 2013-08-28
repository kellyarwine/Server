package http.server.serverSocket;

import http.server.socket.SystemSocket;
import http.server.socket.WebSocket;

import java.io.IOException;
import java.net.ServerSocket;

public class SystemHttpServerSocket implements HttpServerSocket {
  ServerSocket theServerSocket;

  public SystemHttpServerSocket(int port) throws IOException {
    theServerSocket = new ServerSocket(port);
  }
  public WebSocket accept() throws IOException {
    return new SystemSocket(theServerSocket.accept());
  }

}
