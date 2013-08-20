package http.server.serverSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

public interface WebServerSocket {
  void connect()  throws IOException;
  BufferedReader in()          throws IOException;
  OutputStream  out()         throws IOException;
  void closeConnection()       throws IOException;
}
