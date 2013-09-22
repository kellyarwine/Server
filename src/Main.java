import http.io.SystemIo;
import http.server.Server;

public class Main {
  public static void main(String[] args) throws Exception {
    new Server(new SystemIo()).initialize();
  }
}
