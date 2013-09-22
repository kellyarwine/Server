package http.server.socket;

import java.io.*;
import java.net.Socket;

public class SystemSocket implements WebSocket {
  private Socket socket;
  private BufferedReader in;
  private OutputStream out;
  private boolean isClosed;

  public SystemSocket(Socket socket) throws IOException {
    this.socket = socket;
    in = new BufferedReader(new InputStreamReader((socket.getInputStream())));
    out = new BufferedOutputStream(socket.getOutputStream());
    isClosed = false;
  }

  public BufferedReader in() throws IOException {
    return in;
  }

  public OutputStream out() throws IOException {
    return out;
  }

  public void close() throws IOException {
    in.close();
    out.close();
    socket.close();
    isClosed = true;
  }

  public boolean getIsClosed() {
    return isClosed;
  }
}