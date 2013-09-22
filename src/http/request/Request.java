package http.request;

import http.server.socket.WebSocket;

import java.io.IOException;
import java.util.HashMap;

public class Request {
  private HashMap<String, String> requestHash;
  private QueryStringRepository queryStringRepository;

  public Request(QueryStringRepository queryStringRepository) {
    this.queryStringRepository = queryStringRepository;
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
    StringBuffer buffer = new StringBuffer();

    while ( (chr = webSocket.in().read()) != -1 ) {
        buffer.append((char) chr);
          if ( !webSocket.in().ready())
            break;
    }
    return buffer.toString();
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