package http.response.code;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryString {
  private HashMap queryStringHash;

  public QueryString() {
    queryStringHash = new HashMap();
  }

  public byte[] updateBody(byte[] responseBody, HashMap request) {
    String queryString = (String) request.get("queryString");
    if (queryString != null) {
      toHash(queryString);
      String updatedResponseBody = updateWithQueryStringHash(new String(responseBody));
      return updatedResponseBody.getBytes();
    }
    else
      return responseBody;
  }

  private void toHash(String queryString) {
    String[] keyValuePairs = queryString.split("&");
    for(int i=0; i<keyValuePairs.length; i++) {
      String[] keyValuePair = keyValuePairs[i].split("=", 2);
      queryStringHash.put(keyValuePair[0], keyValuePair[1]);
    }
  }

  private String updateWithQueryStringHash(String responseBodyString) {
    Set set = queryStringHash.entrySet();

    for(Iterator i = set.iterator(); i.hasNext();) {
      Map.Entry element = (Map.Entry) i.next();
      String key = (String)element.getKey();
      String value = (String)element.getValue();
      responseBodyString = findAndReplace(responseBodyString, key, fromHexToString(value));
    }

    return responseBodyString;
  }

  private String findAndReplace(String text, String key, String value) {
    return text.replace("{ %" + key + "% }", value);
  }

  private String fromHexToString(String queryString) {
    Pattern pattern = Pattern.compile("\\%..");
    Matcher m = pattern.matcher(queryString);

    while (m.find()) {
      String hex = m.group().substring(1);
      int chr = Integer.parseInt(hex, 16);
      queryString = queryString.replace("%" + hex, "" + (char) chr);
    }

    return queryString;
  }
}