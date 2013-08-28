package http.server;

import http.request.QueryStringRepository;
import http.server.logger.SystemLogger;
import http.server.logger.SystemLoggerFactory;
import http.server.serverSocket.HttpServerSocket;
import http.server.serverSocket.SystemHttpServerSocket;

import java.io.File;
import java.io.IOException;
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
  public ServerThread serverThread;
  public HttpServerSocket serverSocket;

  public void initialize() throws Exception {
    Scanner input = new Scanner(System.in);

    System.out.println("Type \"help\" to see a full list of server commands.");

    while (input.hasNext()) {
        String command = input.nextLine();
        if (command.contains("start server")) {
          Map<String, String> serverConfig = getServerConfig(command);
          run(serverConfig);
        }
        else if (command.contains("start cob_spec"))
          run(cobSpecSettings());
        else if (command.contains("help"))
          helpText();
        else if (command.contains("stop server")) {
          stop();
          break;
        }
        else
          helpText();
    }
  }

  public void run(Map<String, String> serverConfig) throws IOException {
    String port = serverConfig.get("port");
    int portNumber = Integer.parseInt(port);
    HttpServerSocket serverSocket = new SystemHttpServerSocket(portNumber);
    SystemLogger logger = SystemLoggerFactory.build(serverConfig.get("env"));

    logger.logMessage("Ninja Server is running in " + serverConfig.get("env").toString().toUpperCase() + " mode on port " + serverConfig.get("port") + ".  WOOT!");
    logger.logMessage("Now serving files from: " + serverConfig.get("publicDirectoryPath").toString());
    logger.logMessage("Working directory: " + serverConfig.get("workingDirectoryPath"));
    logger.logMessage("Routes Filename: " + serverConfig.get("routesFilePath").toString());
    logger.logMessage(".htaccess Filename: " + serverConfig.get("htAccessFilePath").toString() + "\n");

    QueryStringRepository queryStringRepository = new QueryStringRepository();

    int cores = Runtime.getRuntime().availableProcessors();
    ExecutorService threadPool = Executors.newFixedThreadPool(cores);

    while (true) {
      ServerThreadFactory serverThreadFactory = new ServerThreadFactory();
      serverThread = serverThreadFactory.build(serverConfig, logger, serverSocket.accept(), queryStringRepository);
      threadPool.submit((Runnable)serverThread);
    }
  }

  private static void stop() {
    System.out.println("Ninja Server has stopped running.");
    System.exit(1);
  }

  private static Map getServerConfig(String serverConfigString) {
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

  private Map cobSpecSettings() {
    Map<String, String> serverConfig = new HashMap<String, String>();
    serverConfig.put("port", DEFAULT_PORT);
    serverConfig.put("publicDirectoryPath", COBSPEC_PUBLIC_DIRECTORY_PATH);
    serverConfig.put("env", DEFAULT_ENV);
    serverConfig.put("routesFilePath", DEFAULT_ROUTES_FILE_PATH);
    serverConfig.put("htAccessFilePath", DEFAULT_HTACCESS_FILE_PATH);
    serverConfig.put("workingDirectoryPath", COBSPEC_WORKING_DIRECTORY_PATH);
    return serverConfig;
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
    System.out.println( "   stop server     Stops the server.");
    System.out.println( "   help            Provides detailed information for each command.");
  }

  public static void main(String[] args) throws Exception {
    new Server().initialize();
  }
}