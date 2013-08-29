package http.server;

import http.request.QueryStringRepository;
import http.server.logger.SystemLogger;
import http.server.socket.WebSocket;

import java.util.Map;

public class ServerRequestThreadFactory {
  public ServerRequestThread build(Map serverConfig, SystemLogger logger, WebSocket socket, QueryStringRepository queryStringRepository) {
    if (serverConfig.get("env").equals("production"))
      return new SystemServerRequestThread(serverConfig, logger, socket, queryStringRepository);
    else
      return new MockServerRequestThread();
    }
}
