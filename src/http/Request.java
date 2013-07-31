package http;

public class Request {
  public String httpMethod;
  public String fullURL;
  public String httpProtocol;
  public String baseURL;
  public String queryString;

  public void parseMessage(String message) {
    if (message.startsWith("GET")) {
      parseMessageHeader(message);
      handleGetQueryString();
    }
    else if (message.startsWith("POST")) {
      String[] messageParts = parseMessageParts(message);
      parseMessageHeader(messageParts[0]);
      handlePostQueryString(messageParts[1]);
    }
  }

  private void parseMessageHeader(String header) {
    String[] headerArray = header.split(" ");
    httpMethod = headerArray[0];
    fullURL = headerArray[1];
    httpProtocol = headerArray[2];
    baseURL = fullURL;
    queryString = null;
  }

  private void handleGetQueryString() {
    if (fullURL.contains("?")) {
      String[] urlArray = fullURL.split("\\?");
      baseURL = urlArray[0];
      queryString = urlArray[1];
    }
  }

  private String[] parseMessageParts(String message) {
    return message.split("\r\n\r\n");
  }

  private void handlePostQueryString(String queryString) {
    this.queryString = queryString;
  }
}