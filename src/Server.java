import java.io.*;
import java.net.ServerSocket;
import java.util.Arrays;

public class Server {
  private int port;
  private String directory;
  private ServerSocket theServerSocket;
  OutputStream out;

  public Server(String port, String directory) throws UnsupportedEncodingException {
    this.port = Integer.parseInt(port);
    this.directory = directory;
  }

  public static void main(String[] args) throws Exception {
      int portIndex = Arrays.asList(args).indexOf("-p");
      int directoryIndex = Arrays.asList(args).indexOf("-d");

      Server theServer = new Server(args[portIndex+1], args[directoryIndex+1]);
      theServer.startServer();
    }

  public void startServer() throws Exception {
    try {
      theServerSocket = new ServerSocket(port);
      System.out.println("Server is running on port " + port + ".");

      HTTPBrowser theHTTPBrowser = new HTTPBrowser(theServerSocket, directory);
      theHTTPBrowser.openSocket();
    }

    catch (IOException e) {
      System.err.println(e);
    }
  }

  public void stopServer() {
  }
}