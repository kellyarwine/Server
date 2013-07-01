package http;

import java.io.IOException;

public class ServerSocketFactory {

  public WebServerSocket get(String environment, int port) throws IOException {
    if (environment == "production")
      return new SystemServerSocket(port);
    else
      return new MockServerSocket("This is some text.");
  }
}
