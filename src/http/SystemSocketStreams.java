package http;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SystemSocketStreams implements WebSocketStreams {
  public Socket theConnection;

  public SystemSocketStreams(ServerSocket theServerSocket) throws IOException {
    while (true) {
      Socket theConnection = theServerSocket.accept();
    }
  }

  @Override
  public InputStream in() throws IOException {
    return new BufferedInputStream(theConnection.getInputStream());
  }

  @Override
  public OutputStream out() throws IOException {
    return new BufferedOutputStream(theConnection.getOutputStream());
  }
}
