package http;

import java.io.File;
import java.util.HashMap;

public class Router {
  public static final String NOT_FOUND = "/404.html";
  private String publicDirectory;
  private HashMap<String, String> routes;

	public Router(String publicDirectory, HashMap routes) {
    this.publicDirectory = publicDirectory;
    this.routes = routes;
    this.routes.put("/", "/index.html");
    this.routes.put(null, NOT_FOUND);
	}

  public String get(String route) {
    if (fileValid(route))
      return publicDirectory + route;
    else
      return publicDirectory + routes.get(route);
  }

  private boolean isDynamic(String route) {
    return routes.containsKey(route);
  }

  public boolean fileValid(String filename) {
    if(filename.equals("/"))
      return false;

	  File theFile = new File(publicDirectory + filename);
	  return theFile.canRead() ;
  }


}
