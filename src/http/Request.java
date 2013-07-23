package http;

public class Request {

  public String httpMethod;
  public String fullURL;
  public String httpProtocol;
  public String baseURL;
  public String queryString;

  public void parseHeader(String header) {
    String[] requestArray = header.split(" ");
    httpMethod = requestArray[0];
    fullURL = requestArray[1];
    httpProtocol = requestArray[2];
    getBaseURLAndQueryString();
  }

  public void getBaseURLAndQueryString() {
    if (fullURL.contains("?")) {
      String[] urlArray = fullURL.split("\\?");
      baseURL = urlArray[0];
      queryString = urlArray[1];
    }
    else
      baseURL = fullURL;
  }

  public void parseBody(String body) {
    queryString = body;
  }
}