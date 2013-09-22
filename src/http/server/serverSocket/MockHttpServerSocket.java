package http.server.serverSocket;

import http.server.socket.MockSocket;
import http.server.socket.WebSocket;

import java.io.IOException;
import java.util.List;

public class MockHttpServerSocket implements HttpServerSocket {
  List<String> requests;
  private boolean isClosed;

  public MockHttpServerSocket(List<String> requests) throws IOException {
    this.requests = requests;
    isClosed = false;
  }

  public WebSocket accept() throws IOException {
      String request = requests.remove(0);
      return new MockSocket(request);
  }

  public void close() {
    isClosed = true;
  }

  public boolean isClosed() {
    return isClosed;
  }

  public boolean isBound() {
    return false;
  }
}