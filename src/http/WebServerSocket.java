package http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface WebServerSocket {
  void connect()  throws IOException;
  InputStream   in()          throws IOException;
  OutputStream  out()         throws IOException;
  void closeConnection()       throws IOException;
}
