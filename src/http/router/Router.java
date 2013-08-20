package http.router;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Router {
  private DefaultHashMap routeMap;

  public Router(String publicDirectory, String routesFilePath, String htAccessFilePath) throws IOException {
    RouterMapBuilder routerMapBuilder = new RouterMapBuilder();
    routeMap = routerMapBuilder.buildFrom(publicDirectory, routesFilePath, htAccessFilePath);
  }

  public ArrayList getRouteInfo(HashMap request) {
    String requestedUrl = (String)request.get("url");
    return (ArrayList)routeMap.get(requestedUrl);
  }
}