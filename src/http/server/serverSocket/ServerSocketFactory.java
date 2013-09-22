package http.server.serverSocket;

import java.io.IOException;
import java.util.ArrayList;

public class ServerSocketFactory {
  public static HttpServerSocket build(String env, int port, ArrayList<String> requests) throws IOException {
    if (env.equals("production"))
      return new SystemHttpServerSocket(port);
    else {
      return new MockHttpServerSocket(requests);
    }
  }

}
