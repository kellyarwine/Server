
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {
  OutputStream out;
  private int port;
  private String directory;
  private String startCommand;
  public ServerSocket theServerSocket;

  public Server(String port, String directory) {
    this.port = Integer.parseInt(port);
    this.directory = directory;
  }

  public static void main(String[] args) throws UnsupportedEncodingException {

    int portIndex = Arrays.asList(args).indexOf("-p");
    int directoryIndex = Arrays.asList(args).indexOf("-d");

    Server theServer = new Server(args[portIndex+1], args[directoryIndex+1]);
    theServer.startServer();
  }

  public void startServer() {

    try {
      theServerSocket = new ServerSocket(port);
      System.out.println("Server is running on port " + port + ".");

      while (true) {

        Socket theConnection = theServerSocket.accept();

        out = new BufferedOutputStream(theConnection.getOutputStream());

        String data = "HTTP/1.0 200 OK\r\n"
            + "Server: KellyServer 1.0 \r\n"
            + "Content-length: 52\r\n"
            + "Content-type: text/html\r\n\r\n"
            + "<html><head></head><body>Hi, everyone!</body></html>";

        byte[] dataInBytes = data.getBytes("ASCII");

        out.write(dataInBytes);
        out.flush();

      }
    }

    catch (IOException e) {
      System.err.println(e);
    }

  }

  public void stopServer() {

  }
}
