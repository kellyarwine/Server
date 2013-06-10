
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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

      }  //end 2nd while
    }  //end 2nd try

    catch (IOException e) {
      System.err.println(e);
    }

//    finally {
//      if (theConnection != null) theConnection.close();
//    }

  }

  public void stopServer() {

  }
}
