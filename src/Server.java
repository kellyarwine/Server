import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {
  private byte[] contentInBytes;
  private int port;
  private String directory;
  private byte[] headerInBytes;
  private ServerSocket theServerSocket;
  OutputStream out;

  public Server(byte[] content, String MIMEType, String port, String directory) throws UnsupportedEncodingException {
    this.port = Integer.parseInt(port);
    this.directory = directory;
    this.contentInBytes = content;

    String header = "HTTP/1.0 200 OK\r\n"
        + "Server: KellyServer 1.0 \r\n"
        + "Content-length: " + this.contentInBytes.length + "\r\n"
        + "Content-type: " + MIMEType + "\r\n\r\n";

    this.headerInBytes = header.getBytes("ASCII");
  }

  public static void main(String[] args) throws UnsupportedEncodingException {
    try {

    int portIndex = Arrays.asList(args).indexOf("-p");
    int directoryIndex = Arrays.asList(args).indexOf("-d");

    InputStream in = new FileInputStream("public/the_goal.txt");
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int b;
    while (( b = in.read()) != -1) out.write(b);
    byte[] content = out.toByteArray();

    Server theServer = new Server(content, "text/plain", args[portIndex+1], args[directoryIndex+1]);
    theServer.startServer();
    }

    catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("Usage: java SingleFileHTTPServer filename port encoding");
    }

    catch (Exception e) {
      System.err.println(e);
    }

  }

  public void startServer() {
    try {
      theServerSocket = new ServerSocket(port);
      System.out.println("Server is running on port " + port + ".");

      while (true) {
        Socket theConnection = theServerSocket.accept();
        out = new BufferedOutputStream(theConnection.getOutputStream());
        out.write(headerInBytes);
        out.write(contentInBytes);
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