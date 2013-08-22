package http.server;

import http.server.logger.ConsoleLogger;
import http.server.logger.SystemLogger;
import http.server.serverSocket.SystemServerSocket;
import http.server.serverSocket.WebServerSocket;

import java.io.File;
import java.util.Arrays;

public class Server {
  public Server(WebServerSocket serverSocket, SystemLogger logger, String workingDirectoryPath, String publicDirectory, String routesFilePath, String htAccessFilePath) throws Exception {
    File workingDirectory = new File(workingDirectoryPath);

    logger.logMessage("Ninja Server is running.  WOOT!");
    logger.logMessage("Now serving files from: " + new File(workingDirectory, publicDirectory).toString());
    logger.logMessage("Routes Filename: "        + new File(workingDirectory, routesFilePath).toString());
    logger.logMessage("htaccess Filename: "      + new File(workingDirectory, htAccessFilePath).toString());

    while (true) {
      new ServerThread(serverSocket.accept(), logger, publicDirectory, routesFilePath, htAccessFilePath, workingDirectory).start();
    }
  }

  public static void main(String[] args) throws Exception {
    args = new String[] {"-p", "5000", "-d", "test/public/", "-e", "production", "-r", "routes.csv", "-h", ".htaccess", "-w", "/Users/Kelly/Desktop/Java_HTTP_Server"};
    int portIndex = Arrays.asList(args).indexOf("-p");
    Integer port = Integer.parseInt(args[portIndex + 1]);

    int publicDirectoryIndex = Arrays.asList(args).indexOf("-d");
    String publicDirectory = args[publicDirectoryIndex + 1];

    int envIndex = Arrays.asList(args).indexOf("-e");
    String env = args[envIndex + 1];

    int routesFileIndex = Arrays.asList(args).indexOf("-r");
    String routesFilePath = args[routesFileIndex + 1];

    int htAccessFileIndex = Arrays.asList(args).indexOf("-h");
    String htAccessFilePath = args[htAccessFileIndex + 1];

    int workingDirectoryIndex = Arrays.asList(args).indexOf("-w");
    String workingDirectoryPath = args[workingDirectoryIndex + 1];

    WebServerSocket serverSocket = new SystemServerSocket(port);
    SystemLogger logger = new ConsoleLogger();
    new Server(serverSocket, logger, workingDirectoryPath, publicDirectory, routesFilePath, htAccessFilePath);
  }
}