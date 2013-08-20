package http.request;

import java.util.HashMap;

public class Parser {
  private String[] request;
  private String[] requestLineParts;
  private HashMap<String, String> requestHash;

  public HashMap parse(String requestString) {
    splitRequest(requestString);
    splitRequestLine();
    setRequestHash();
    return requestHash;
  }

  private void splitRequest(String requestString) {
    request = requestString.split("\r\n");
  }

  private void splitRequestLine() {
    requestLineParts = request[0].split("[\\ \\?]");
  }

  private void setRequestHash() {
    setRequestLineData();
    setQueryString();
    setRequestHeaderData();
  }

  private void setRequestLineData() {
    requestHash = new HashMap<String, String>();

    if (requestLineParts.length == 3) {
      requestHash.put("httpMethod", requestLineParts[0]);
      requestHash.put("url", requestLineParts[1]);
      requestHash.put("httpProtocol", requestLineParts[2]);
    }
    else {
      requestHash.put("httpMethod", requestLineParts[0]);
      requestHash.put("url", requestLineParts[1]);
      requestHash.put("queryString", requestLineParts[2]);
      requestHash.put("httpProtocol", requestLineParts[3]);
    }
  }

  private void setQueryString() {
    if (queryStringExists())
      requestHash.put("queryString", request[request.length-1]);
  }

  private void setRequestHeaderData() {
    for(int i = 1; i < request.length; i++) {
      if (isRequestHeader(i)) {
        String[] requestHeader = request[i].split(": ");
        String requestHeaderKey = requestHeader[0];
        String requestHeaderValue = requestHeader[requestHeader.length-1];
        requestHash.put(requestHeaderKey, requestHeaderValue);
      }
    }
  }

  private boolean isRequestHeader(int i) {
    return request[i].contains(": ");
  }

  private boolean queryStringExists() {
    return !isRequestHeader(request.length - 1);
  }
}