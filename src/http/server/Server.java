package http.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
  private static String DEFAULT_PORT = "5000";
  private static String DEFAULT_PUBLIC_DIRECTORY_PATH = "test/public/";
  private static String DEFAULT_ENV = "production";
  private static String DEFAULT_ROUTES_FILE_PATH = "routes.csv";
  private static String DEFAULT_HTACCESS_FILE_PATH = ".htaccess";
  private static String DEFAULT_WORKING_DIRECTORY_PATH = new File(System.getProperty("user.dir")).toString();
  private static String COBSPEC_PUBLIC_DIRECTORY_PATH = "public/";
  private static String COBSPEC_WORKING_DIRECTORY_PATH = new File(new File(System.getProperty("user.dir")).getParent(), "cob_spec").toString();
  public ServerRunnable serverRunnable;
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
    if (serverRunnable == null || serverRunnable.httpServerSocket.isClosed())
      System.out.println("Ninja Server is not running.");
    else
      System.out.println("Ninja Server is running on port " + serverConfig.get("port") + ".");
  }

  private void startServerThread() throws IOException, URISyntaxException {
    if (validateServerConfig(serverConfig) == true) {
      int cores = Runtime.getRuntime().availableProcessors();
      ExecutorService serverThreadPool = Executors.newFixedThreadPool(cores);
      serverRunnable = new ServerRunnable(serverConfig);
      serverThreadPool.submit(serverRunnable);
    }
  }

  private boolean validateServerConfig(Map<String, String> serverConfig) throws IOException {
    return
      checkPortAvailability() &&
      validateEnv() &&
      validateWorkingDirectoryPath() &&
      validatePublicDirectoryPath() &&
      validateRoutesFilePath() &&
      validateHtAccessFilePath();
  }

  private boolean checkPortAvailability() throws IOException {
    int port = Integer.parseInt((String)serverConfig.get("port"));
    try {
      ServerSocket serverSocket = new ServerSocket(port);
      serverSocket.close();
      return true;
    }
    catch (IOException e) {
      System.out.println("Port " + port + " is already in use.  Please try again.");
      return false;
    }
    catch (IllegalArgumentException e) {
      System.out.println("Port " + port + " is not a valid port.  Please try again.");
      return false;
    }
  }

  private boolean validateEnv() {
    String env = (String)serverConfig.get("env");
    if (env.equals("production") || env.equals("test"))
      return true;
    else {
      System.out.println("Invalid \"Env\" setting.");
      return false;
    }
  }

  private boolean validateWorkingDirectoryPath() {
    String workingDirectoryPath = (String)serverConfig.get("workingDirectoryPath");
    if (new File(workingDirectoryPath).exists())
      return true;
    else {
      System.out.println("Working directory does not exist.");
      return false;
    }
  }

  private boolean validatePublicDirectoryPath() {
    String workingDirectoryPath = (String)serverConfig.get("workingDirectoryPath");
    String publicDirectoryPath = (String)serverConfig.get("publicDirectoryPath");
    if (new File(workingDirectoryPath, publicDirectoryPath).exists())
      return true;
    else {
      System.out.println("Public directory does not exist.");
      return false;
    }
  }

  private boolean validateRoutesFilePath() {
    String workingDirectoryPath = (String)serverConfig.get("workingDirectoryPath");
    String routesFilePath = (String)serverConfig.get("routesFilePath");
    if (new File(workingDirectoryPath, routesFilePath).exists())
      return true;
    else {
      System.out.println("Routes file does not exist.");
      return false;
    }
  }

  private boolean validateHtAccessFilePath() {
    String workingDirectoryPath = (String)serverConfig.get("workingDirectoryPath");
    String htAccessFilePath = (String)serverConfig.get("htAccessFilePath");
    if (new File(workingDirectoryPath, htAccessFilePath).exists())
      return true;
    else {
      System.out.println(".htaccess file does not exist.");
      return false;
    }
  }

  private void closeServerSocket() throws IOException {
    if (serverRunnable != null && serverRunnable.httpServerSocket.isClosed() == false) {
      System.out.println("Ninja Server has been shut down.");
      serverRunnable.closeServerSocket();
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
    System.out.println( "Ninja Server Help Menu");
    System.out.println( "-------------------------");
    System.out.println( "Available Commands:");
    System.out.println( " start cob_spec  Starts the server with cob_spec configurations.");
    System.out.println( " status          Lists the status of the server.");
    System.out.println( " stop server     Stops the server.");
    System.out.println( " exit            Exits the application.");
    System.out.println( " help            Provides instructions and detailed information for each command.");
    System.out.println("");
    System.out.println( "Starting the Server:");
    System.out.println( " start server    Starts the server.  The application takes six optional parameters:");
    System.out.println( "                 an environment setting; \"test\" or \"production\" (denoted by the \"-e\" flag)");
    System.out.println( "                 a port number (denoted by the \"-p\" flag)");
    System.out.println( "                 the absolute path to the working directory (denoted by the \"-w\" flag)");
    System.out.println( "                 the relative path to the public directory (denoted by the \"-d\" flag)");
    System.out.println( "                 the Routes filename; file must exist in the root working directory (denoted by the \"-r\" flag)");
    System.out.println( "                 the .htaccess filename; file must exist in the root working directory (denoted by the \"-h\" flag)");
    System.out.println( "Default Server Configurations:");
    System.out.println( " start server    [=<-e " + DEFAULT_ENV + ">]\n" +
                        "                 [=<-p " + DEFAULT_PORT + ">]\n" +
                        "                 [=<-w " + DEFAULT_WORKING_DIRECTORY_PATH + ">]\n" +
                        "                 [=<-d " + DEFAULT_PUBLIC_DIRECTORY_PATH + ">]\n" +
                        "                 [=<-r " + DEFAULT_ROUTES_FILE_PATH + ">]\n" +
                        "                 [=<-h " + DEFAULT_HTACCESS_FILE_PATH + ">]");
  }

  public static void main(String[] args) throws Exception {
    new Server().initialize();
  }
}