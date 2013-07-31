package http;

import java.io.IOException;

public class ServerSocketFactory {

  public WebServerSocket get(int port, String env) throws IOException {
    if (env.equals("production"))
      return new SystemServerSocket(port);
    else
      return new MockServerSocket("This is some text.");
  }
}
