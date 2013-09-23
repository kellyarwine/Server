package http.server.serverSocket;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ServerSocketFactory {
  public HttpServerSocket build(HashMap<String, String> serverConfig) throws IOException {
    String env = serverConfig.get("env");

    if (env.equals("production")) {
      int port = Integer.parseInt(serverConfig.get("port"));
      return new SystemHttpServerSocket(port);
    }
    else {
      String workingDirectoryPath = serverConfig.get("workingDirectoryPath");
      String mockRequestsFilePath = serverConfig.get("mockRequestsFilePath");
      return new MockHttpServerSocket(new File(workingDirectoryPath, mockRequestsFilePath).toString());
    }
  }
}