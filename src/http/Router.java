package http;

import java.io.File;

public class Router {

  public String get(String baseURL, String publicDirectory) {
    String route;

    if (baseURL.equals(publicDirectory + "/"))         route = publicDirectory + "/index.html";
    else if (baseURL.equals(publicDirectory + "/hi"))  route = (publicDirectory + "/hi_everyone.html");
    else if (fileValid(baseURL))                       route = baseURL;
    else                                               route = (publicDirectory + "/404.html");
    return route;
  }

  public boolean fileValid(String baseURL) {
    File theFile = new File(baseURL);

    if (theFile.canRead())
      return true;
    else
      return false;
  }

}
