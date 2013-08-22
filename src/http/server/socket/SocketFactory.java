//package http.server.socket;
//
//import java.io.IOException;
//import java.net.Socket;
//import java.util.ArrayList;
//
//public class SocketFactory {
//
//  public WebSocket get(Socket theSocket, String env, ArrayList requests) throws IOException {
//    if (env.equals("production"))
//      return new SystemSocket(theSocket);
//    else
//      return new MockSocket(requests);
//  }
//}
