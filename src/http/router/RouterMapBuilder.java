package http.router;

import http.response.routeType.FileNotFound;
import http.router.csvMapBuilder.HtAccessMapBuilder;
import http.router.csvMapBuilder.RouteMapBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RouterMapBuilder {
  private File publicDirectoryFullPath;
  private DefaultHashMap routerMap;

  public DefaultHashMap buildFrom(File workingDirectory, String publicDirectory, String routesFilePath, String htAccessFilePath) throws IOException, URISyntaxException {
    publicDirectoryFullPath = new File(workingDirectory, publicDirectory);
    File routesFile = new File(workingDirectory, routesFilePath);
    File htAccessFile = new File(workingDirectory, htAccessFilePath);
    routerMap = getDefaultHashMap();
    putPublicDirectoryFiles(publicDirectoryFullPath);
    putRoutes(routesFile);
    putRedirects(htAccessFile);
    return routerMap;
  }


  private DefaultHashMap getDefaultHashMap() throws IOException {
    ArrayList arrayList = new ArrayList();
    arrayList.add(new File(publicDirectoryFullPath, "/templates/404.html"));
    arrayList.add(new FileNotFound());
    return new DefaultHashMap(arrayList);
  }

  private void putPublicDirectoryFiles(File publicDirectoryFullPath) throws IOException {
    HashMap publicDirectoryMap = createPublicDirectoryMap(publicDirectoryFullPath);
    appendToRouterMap(publicDirectoryMap);
  }

  private void putRoutes(File routesFile) throws IOException {
    HashMap routesMap = createRoutesMap(routesFile);
    appendToRouterMap(routesMap);
  }

  private void putRedirects(File htAccessFile) throws IOException {
    HashMap htAccessMap = createHtaccessMap(htAccessFile);
    appendToRouterMap(htAccessMap);
  }

  private HashMap createPublicDirectoryMap(File publicDirectoryFullPath) throws IOException {
    PublicDirectoryMapBuilder publicDirectoryMapBuilder = new PublicDirectoryMapBuilder();
    return publicDirectoryMapBuilder.get(publicDirectoryFullPath);
  }

  private HashMap createRoutesMap(File routesFile) throws IOException {
    RouteMapBuilder routeMapBuilder = new RouteMapBuilder(publicDirectoryFullPath);
    return routeMapBuilder.get(routesFile);
  }

  private HashMap createHtaccessMap(File htAccessFile) throws IOException {
    HtAccessMapBuilder htAccessMapBuilder = new HtAccessMapBuilder(publicDirectoryFullPath);
    return htAccessMapBuilder.get(htAccessFile);
  }

  private void appendToRouterMap(HashMap map) {
    Iterator iterator = map.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry entry = (Map.Entry)iterator.next();
      routerMap.put(entry.getKey(), entry.getValue());
    }
  }
}