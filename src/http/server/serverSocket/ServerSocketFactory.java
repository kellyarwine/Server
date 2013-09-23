package http.server.serverSocket;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ServerSocketFactory {
  public HttpServerSocket build(HashMap<String, String> serverConfig) throws IOException {
    String env = serverConfig.get("env");
    int port = Integer.parseInt(serverConfig.get("port"));
    String workingDirectoryPath = serverConfig.get("workingDirectoryPath");
    String mockRequestsFilePath = serverConfig.get("mockRequestsFilePath");
    String mockRequestsFullPathString = new File(workingDirectoryPath, mockRequestsFilePath).toString();

    if (env.equals("production"))
      return new SystemHttpServerSocket(port);
    else
      return new MockHttpServerSocket(mockRequestsFullPathString);
  }
}