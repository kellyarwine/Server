package http.request;

import java.util.HashMap;

public class QueryStringRepository {
  private HashMap<String, String> queryStringHash;

  public QueryStringRepository() {
    queryStringHash = new HashMap<String, String>();
  }

  public void save(String url, String queryString) {
    queryStringHash.put(url, queryString);
  }

  public String getQueryString(String url) {
    return queryStringHash.get(url);
  }
}
