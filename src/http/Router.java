package http;

import java.io.File;

public class Router {

  public String get(String baseURL, String publicDirectory) {
    String route = publicDirectory;

    if (baseURL.equals("/"))                           route += "/index.html";
    else if (baseURL.equals("/hi"))                    route += "/hi_everyone.html";
    else if (fileValid(publicDirectory + baseURL))     route += baseURL;
    else                                               route += "/404.html";
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
