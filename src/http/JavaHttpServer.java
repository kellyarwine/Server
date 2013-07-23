package http;

import java.util.Arrays;

public class JavaHttpServer {
  public static void main(String[] args) throws Exception {
    int portIndex = Arrays.asList(args).indexOf("-p");
    Integer port = Integer.parseInt(args[portIndex + 1]);

    int publicDirectoryIndex = Arrays.asList(args).indexOf("-d");
    String publicDirectory = args[publicDirectoryIndex + 1];

    Server server = new Server(port, publicDirectory);
    server.run();
  }
}