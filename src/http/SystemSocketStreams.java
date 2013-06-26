package http;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SystemSocketStreams implements WebSocketStreams {
  public Socket theConnection;
  private InputStream input;
  private OutputStream output;

  public SystemSocketStreams(ServerSocket theServerSocket) throws IOException {
    theConnection = theServerSocket.accept();
    input = new BufferedInputStream(theConnection.getInputStream());
    output = new BufferedOutputStream(theConnection.getOutputStream());
  }

  @Override
  public InputStream in() throws IOException {
    return input;
  }

  @Override
  public OutputStream out() throws IOException {
    return output;
  }
}
