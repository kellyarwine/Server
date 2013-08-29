package http.server;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Server {
  private static String DEFAULT_PORT = "5000";
  private static String DEFAULT_PUBLIC_DIRECTORY_PATH = "test/public/";
  private static String DEFAULT_ENV = "production";
  private static String DEFAULT_ROUTES_FILE_PATH = "routes.csv";
  private static String DEFAULT_HTACCESS_FILE_PATH = ".htaccess";
  private static String DEFAULT_WORKING_DIRECTORY_PATH = new File(System.getProperty("user.dir")).toString();
  private static String COBSPEC_PUBLIC_DIRECTORY_PATH = "public/";
  private static String COBSPEC_WORKING_DIRECTORY_PATH = new File(new File(System.getProperty("user.dir")).getParent(), "cob_spec").toString();
  public ServerThread serverThread;
  private Map serverConfig;

  public void initialize() throws Exception {
    Scanner input = new Scanner(System.in);

    System.out.println("Ninja Server Menu");
    System.out.println("----------------------");
    System.out.println("Type \"help\" to see a list of available commands.");

    while (true) {
        String command = input.nextLine();
        if (command.contains("start server")) {
          serverConfig = getServerConfig(command);
          startServerThread();
        }
        else if (command.contains("start cob_spec")) {
          serverConfig = getCobSpecServerConfig();
          startServerThread();
        }
        else if (command.contains("status"))
          serverStatus();
        else if (command.contains("help"))
          helpText();
        else if (command.contains("stop server")) {
          closeServerSocket();
        }
        else if (command.contains("exit")) {
          closeServerSocket();
          System.exit(1);
        }
        else
          helpText();
    }
  }

  private void serverStatus() throws IOException {
    if (serverThread == null || serverThread.httpServerSocket.isClosed())
      System.out.println("Ninja Server is not running.");
    else
      System.out.println("Ninja Server is running on port " + serverConfig.get("port") + ".");
  }

  private void startServerThread() throws IOException {
    if (serverThread == null || serverThread.httpServerSocket.isClosed() == true) {
      serverThread = new ServerThread(serverConfig);
      new Thread(serverThread).start();
    }
    else {
      System.out.println("Ninja Server is already running!");
    }
  }

  private void closeServerSocket() throws IOException {
    if (serverThread.httpServerSocket.isClosed() == false) {
      System.out.println("Ninja Server has been shut down.");
      serverThread.closeServerSocket();
    }
    else
    System.out.println("Ninja Server is not currently running.");
  }

  private Map getServerConfig(String serverConfigString) {
    String[] serverConfigArray = serverConfigString.split(" ");
    Map<String, String> serverConfig = new HashMap<String, String>();

    int portIndex = Arrays.asList(serverConfigArray).indexOf("-p");
    String port = (portIndex == -1) ? DEFAULT_PORT : serverConfigArray[portIndex + 1];
    serverConfig.put("port", port);

    int publicDirectoryPathIndex = Arrays.asList(serverConfigArray).indexOf("-d");
    String publicDirectoryPath = (publicDirectoryPathIndex == -1) ? DEFAULT_PUBLIC_DIRECTORY_PATH : serverConfigArray[publicDirectoryPathIndex + 1];
    serverConfig.put("publicDirectoryPath", publicDirectoryPath);

    int envIndex = Arrays.asList(serverConfigArray).indexOf("-e");
    String env = (envIndex == -1) ? DEFAULT_ENV : serverConfigArray[envIndex + 1];
    serverConfig.put("env", env);

    int routesFileIndex = Arrays.asList(serverConfigArray).indexOf("-r");
    String routesFilePath = (routesFileIndex == -1) ? DEFAULT_ROUTES_FILE_PATH : serverConfigArray[routesFileIndex + 1];
    serverConfig.put("routesFilePath", routesFilePath);

    int htAccessFileIndex = Arrays.asList(serverConfigArray).indexOf("-h");
    String htAccessFilePath = (htAccessFileIndex == -1) ? DEFAULT_HTACCESS_FILE_PATH : serverConfigArray[htAccessFileIndex + 1];
    serverConfig.put("htAccessFilePath", htAccessFilePath);

    int workingDirectoryPathIndex = Arrays.asList(serverConfigArray).indexOf("-w");
    String workingDirectoryPath = (workingDirectoryPathIndex == -1) ? DEFAULT_WORKING_DIRECTORY_PATH : serverConfigArray[workingDirectoryPathIndex + 1];
    serverConfig.put("workingDirectoryPath", workingDirectoryPath);

    return serverConfig;
  }

  private Map getCobSpecServerConfig() {
    Map<String, String> cobSpecServerConfig = new HashMap<String, String>();
    cobSpecServerConfig.put("port", DEFAULT_PORT);
    cobSpecServerConfig.put("publicDirectoryPath", COBSPEC_PUBLIC_DIRECTORY_PATH);
    cobSpecServerConfig.put("env", DEFAULT_ENV);
    cobSpecServerConfig.put("routesFilePath", DEFAULT_ROUTES_FILE_PATH);
    cobSpecServerConfig.put("htAccessFilePath", DEFAULT_HTACCESS_FILE_PATH);
    cobSpecServerConfig.put("workingDirectoryPath", COBSPEC_WORKING_DIRECTORY_PATH);
    return cobSpecServerConfig;
  }

  private void helpText() {
    System.out.println( "");
    System.out.println( "Ninja Server Functions:");
    System.out.println( "   start server    Starts the server.");
    System.out.println( "   usage:          start server [=<-p " + DEFAULT_PORT + ">]\n" +
        "                                [=<-d " + DEFAULT_PUBLIC_DIRECTORY_PATH + ">]\n" +
        "                                [=<-e " + DEFAULT_ENV + ">]\n" +
        "                                [=<-r " + DEFAULT_ROUTES_FILE_PATH + ">]\n" +
        "                                [=<-h " + DEFAULT_HTACCESS_FILE_PATH + ">]\n" +
        "                                [=<-w " + DEFAULT_WORKING_DIRECTORY_PATH + ">]");
    System.out.println( "   start cob_spec  Starts the server with cob_spec configurations.");
    System.out.println( "   status          Lists the status of the server.");
    System.out.println( "   stop server     Stops the server.");
    System.out.println( "   exit            Exits the application.");
    System.out.println( "   help            Lists detailed information for each command.");
  }

  public static void main(String[] args) throws Exception {
    new Server().initialize();
  }
}