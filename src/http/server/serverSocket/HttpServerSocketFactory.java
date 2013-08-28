package http.server.serverSocket;

import java.io.IOException;
import java.util.List;

public class HttpServerSocketFactory {
  public HttpServerSocket build(String env, List args) throws IOException {
    int portNumber = Integer.parseInt((String)args.get(0));

    if (env.equals("production"))
      return new SystemHttpServerSocket(portNumber);
    else
      return new MockHttpServerSocket(args);
  }
}
