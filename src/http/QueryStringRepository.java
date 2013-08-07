package http;

import java.util.HashMap;

public class QueryStringRepository {
  public HashMap previousQueryStringHash;

  public QueryStringRepository() {
    previousQueryStringHash = new HashMap();
  }

  public void saveQueryString(String httpMethod, HashMap previousQueryStringHash) {
    if (!httpMethod.equals("GET"))
      this.previousQueryStringHash = previousQueryStringHash;
  }
}
