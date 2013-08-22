    package http.server.socket;

    import java.io.*;
    import java.net.Socket;

    public class SystemSocket implements WebSocket {
      private Socket theConnection;
      private BufferedReader in;
      private OutputStream out;

      public SystemSocket(Socket theConnection) throws IOException {
        this.theConnection = theConnection;
        in = new BufferedReader(new InputStreamReader((theConnection.getInputStream())));
        out = new BufferedOutputStream(theConnection.getOutputStream());
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
        theConnection.close();
      }

      public boolean isClosed() throws IOException {
        return theConnection.isClosed();
      }
    }