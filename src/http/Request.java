package http;

public class Request {

  public String httpMethod;
  public String fullURL;
  public String httpProtocol;
  public String baseURL;
  public String queryString;

  public void parse(String request) {
    System.out.println(request);

    String[] requestArray = request.split(" ");

    httpMethod = requestArray[0];
    fullURL = requestArray[1];
    httpProtocol = requestArray[2];

    handleURL();
  }

  public void handleURL() {
    if (fullURL.contains("?")) {
      String[] urlArray = fullURL.split("\\?");
      baseURL = urlArray[0];
      queryString = urlArray[1];
    }
    else
      baseURL = fullURL;
  }

}