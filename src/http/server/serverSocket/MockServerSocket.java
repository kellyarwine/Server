package http.server.serverSocket;

import http.server.socket.MockSocket;
import http.server.socket.WebSocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MockServerSocket implements WebServerSocket {
  List<String> requests;

  public MockServerSocket(ArrayList requests) throws IOException {
    this.requests = requests;
  }

  public WebSocket accept() throws IOException {
    if (!requests.isEmpty()) {
      String request = requests.remove(0);
      return new MockSocket(request);
    }
    String eof = new String(new byte[] {-1});
    return new MockSocket(eof);
  }

}
