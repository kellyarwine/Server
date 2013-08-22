    package http.server.socket;

    import java.io.*;
    import java.net.Socket;

    public class SystemSocket implements WebSocket {
      private Socket theConnection;
      private InputStream in;
      private OutputStream out;

      public SystemSocket(Socket theConnection) throws IOException {
        this.theConnection = theConnection;
        in = theConnection.getInputStream();
        out = new BufferedOutputStream(theConnection.getOutputStream());
      }

      public InputStream in() throws IOException {
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