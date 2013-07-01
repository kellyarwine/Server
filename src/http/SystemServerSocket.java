package http;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SystemServerSocket implements WebServerSocket {
  public ServerSocket theServerSocket;
  public Socket theConnection;
  public InputStream in;
  public OutputStream out;

  public SystemServerSocket(int port) throws IOException {
    theServerSocket = new ServerSocket(port);
  }

  public void connect() throws IOException {
    theConnection = theServerSocket.accept();
    in = new BufferedInputStream(theConnection.getInputStream());
    out = new BufferedOutputStream(theConnection.getOutputStream());
  }

  public InputStream in() throws IOException {
    return in;
  }

  public OutputStream out() throws IOException {
    return out;
  }

  public void closeConnection() throws IOException {
    theConnection.close();
  }

}
