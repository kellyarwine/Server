package http;

import java.util.Arrays;

public class Configurations {
  public int port;
  public String publicDirectory;
  public String env;

  public String get(String[] args){
    if (args.length == 0)
      return outputInstructions();
    else {
      parse(args);
      return "";
    }
  }

  private String outputInstructions() {
    String instructions =
          "Starting the server: server_start [-p [port number]] [-d [public directory path]] [-e [environment]]" + "\r\n"
        + "\r\n\r\n"
        + "The most commonly used server configurations are:" + "\r\n"
        + "-p [port number]        Add file contents to the index" + "\r\n"
        + "-d [public directory path]     Find by binary search the change that introduced a bug" + "\r\n"
        + "-e [environment]     List, create, or delete branches" + "\r\n"
        + "\r\n\r\n"
        + "Stopping the server: server_stop" + "\r\n"
        + "\r\n\r\n"
        + "See 'server_help <configuration flag>' for more information on a specific command." + "\r\n";
    return instructions;
  }

  public void parse(String[] args) {
    int portIndex = Arrays.asList(args).indexOf("-p");
    port = Integer.parseInt(args[portIndex+1]);

    int publicDirectoryIndex = Arrays.asList(args).indexOf("-d");
    publicDirectory = args[publicDirectoryIndex+1];

    int envIndex = Arrays.asList(args).indexOf("-e");
    env = args[envIndex+1];
  }
}
