package http.request;

import http.server.socket.WebSocket;

import java.io.IOException;
import java.util.HashMap;

public class Request {
  private HashMap<String, String> requestHash;
  private QueryStringRepository queryStringRepository;

  public Request() {
    queryStringRepository = new QueryStringRepository();
  }

  public HashMap get(WebSocket webSocket) throws IOException {
    String requestString = receive(webSocket);
    requestHash = new Parser().parse(requestString);
    savePostOrPutQueryString();
    updateGetQueryStringWithRepositoryData();
    return requestHash;
  }

    private String receive(WebSocket webSocket) throws IOException {
      int chr;
      System.out.println("Receiving!");
      System.out.println("webSocket.in().available() = " + webSocket.in().available());
      StringBuffer buffer = new StringBuffer();

      while ( webSocket.in().available() > 0 ) {
        chr = readNextChar(webSocket);
        buffer.append((char) chr);
      }
      System.out.println("buffer.length() = " + buffer.length());
      return buffer.toString();
    }

  private int readNextChar(WebSocket webSocket) {
    try {
      if ( webSocket.in().available () > 0 )
        return webSocket.in().read();
      else return -1;
    }
    catch (IOException e) {
      return -1;
    }
  }

  private void savePostOrPutQueryString() {
    String url         = requestHash.get("url");
    String queryString = requestHash.get("queryString");

    if (!isGet() && queryString != null)
      queryStringRepository.save(url, queryString);
  }

  private void updateGetQueryStringWithRepositoryData() {
    if (isGet() && savedQueryString() != null)
      requestHash.put("queryString", savedQueryString());
  }

  private String savedQueryString() {
    return queryStringRepository.getQueryString(requestHash.get("url"));
  }

  private boolean isGet() {
    return requestHash.get("httpMethod").equals("GET");
  }
}