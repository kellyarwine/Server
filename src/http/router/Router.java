package http.router;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

public class Router {
  private DefaultHashMap routeMap;

  public Router(File workingDirectory, String publicDirectory, String routesFilePath, String htAccessFilePath) throws IOException, URISyntaxException {
    RouterMapBuilder routerMapBuilder = new RouterMapBuilder();
    routeMap = routerMapBuilder.buildFrom(workingDirectory, publicDirectory, routesFilePath, htAccessFilePath);
  }

  public ArrayList getRouteInfo(HashMap request) {
    String requestedUrl = (String)request.get("url");
    return (ArrayList)routeMap.get(requestedUrl);
  }
}