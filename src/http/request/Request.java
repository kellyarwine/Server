package http.request;

import http.server.serverSocket.WebServerSocket;

import java.io.IOException;
import java.util.HashMap;

public class Request {
  public HashMap<String, String> requestHash;
  private WebServerSocket theServerSocket;
  private QueryStringRepository queryStringRepository;

  public Request() {
    queryStringRepository = new QueryStringRepository();
  }

  public HashMap get(WebServerSocket theServerSocket) throws IOException {
    String requestString = receive(theServerSocket);
    requestHash = new Parser().parse(requestString);
    savePostOrPutQueryString();
    updateGetQueryStringWithRepositoryData();
    return requestHash;
  }

  private String receive(WebServerSocket theServerSocket) throws IOException {
    int chr;
    StringBuffer buffer = new StringBuffer();
    while ( (chr = theServerSocket.in().read()) != -1) {
      buffer.append((char) chr);
      if (!theServerSocket.in().ready()) break;
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