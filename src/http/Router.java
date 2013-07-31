package http;

import java.io.File;
import java.util.HashMap;

public class Router {
  public static final String NOT_FOUND = "/404.html";
  private File publicDirectoryFolder;
  private HashMap routeMap;

  public Router(String publicDirectory, HashMap<String, String> routes) {
    File workingDirectory = new File(System.getProperty("user.dir"));
    this.publicDirectoryFolder = new File(workingDirectory, publicDirectory);
    DefaultHashMapBuilder builder = new DefaultHashMapBuilder(NOT_FOUND);
    this.routeMap = builder.buildFrom(routes);
	}

  public File get(String route) {
    File routeFile = new File(publicDirectoryFolder, route);
    if (fileValid(routeFile))  {
      return routeFile;
    }
    else;
      return new File(publicDirectoryFolder, (String) routeMap.get(route));
  }

  public boolean fileValid(File routeFile) {
	  return routeFile.canRead() ;
  }
}