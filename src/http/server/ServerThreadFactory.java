package http.server;

import http.request.QueryStringRepository;
import http.server.logger.SystemLogger;
import http.server.serverSocket.MockServerThread;
import http.server.socket.WebSocket;

import java.util.Map;

public class ServerThreadFactory {
  public ServerThread build(Map serverConfig, SystemLogger logger, WebSocket socket, QueryStringRepository queryStringRepository) {
    if (serverConfig.get("env").equals("production"))
      return new SystemServerThread(serverConfig, logger, socket, queryStringRepository);
    else
      return new MockServerThread();
    }
}
