package http.router.csvMapBuilder;

import http.response.routeType.Public;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class RouteMapBuilder extends CsvMapBuilder {
  private String CHARACTER_DELIMITER = ",";

  public RouteMapBuilder(File publicDirectoryFullPath) {
    super(publicDirectoryFullPath);
  }

  public HashMap createMap(String[] lines) {
    HashMap hashMap = new HashMap();
    for (int i=0; i<lines.length; i++) {
      String line = lines[i];
      String[] lineArray = line.split(CHARACTER_DELIMITER);
      String url = lineArray[0].trim();
      String route = lineArray[1].trim();
      ArrayList routeInfo = createRouteInfo(route);
      hashMap.put(url, routeInfo);
    }
    return hashMap;
  }

  private ArrayList createRouteInfo(String route) {
    ArrayList routeInfo = new ArrayList();
    routeInfo.add(new File(publicDirectoryFullPath, route));
    routeInfo.add(new Public());
    return routeInfo;
  }
}