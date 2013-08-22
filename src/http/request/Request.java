package http.request;

import http.EmptyRequestException;
import http.server.socket.WebSocket;

import java.io.IOException;
import java.util.HashMap;

public class Request {
  private HashMap<String, String> requestHash;
  private QueryStringRepository queryStringRepository;

  public Request() {
    queryStringRepository = new QueryStringRepository();
  }

  public HashMap get(WebSocket webSocket) throws IOException, EmptyRequestException {
    String requestString = new RequestHandler().receive(webSocket);
    requestHash = new Parser().parse(requestString);
    savePostOrPutQueryString();
    updateGetQueryStringWithRepositoryData();
    return requestHash;
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