package http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public interface WebSocketStreams {
  Socket listen() throws IOException;
  InputStream  in() throws IOException;
  OutputStream out() throws IOException;
}
