package http.server.serverSocket;

import http.server.socket.MockSocket;
import http.server.socket.WebSocket;

import java.io.IOException;
import java.util.List;

public class MockHttpServerSocket implements HttpServerSocket {
  List<String> requests;

  public MockHttpServerSocket(List<String> requests) throws IOException {
    this.requests = requests;
  }

  public WebSocket accept() throws IOException {
//    if (!requests.isEmpty()) {
//      String request = requests.remove(0);
//      System.out.println(request);
//      return new MockSocket(request);
//    }
//    else {
//      String eOF = new String(new byte[] { -1 });
//      System.out.println("I am returning eof.");
//      return new MockSocket(eOF);
//    }
//    String request = requests.remove(0);
    return new MockSocket();
  }
}