package http.router;

import http.response.routeType.FileNotFound;
import http.router.csvMapBuilder.HtAccessMapBuilder;
import http.router.csvMapBuilder.RouteMapBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
//TODO:  I'm not handling deleting the templates once I'm done.
//May need to find tests that call the templater and delete templates in teardown.
public class RouterMapBuilder {
  private String FOUR_HUNDRED_FOUR_TEMPLATE = "404.html";
  private String FILE_DIRECTORY_TEMPLATE = "file_directory.html";
  private String FORM_TEMPLATE = "form.html";
  private String PARAMETERS_TEMPLATE = "parameters.html";
  private File publicDirectoryFullPath;
  private DefaultHashMap routerMap;

  public DefaultHashMap buildFrom(String publicDirectory, String routesFilePath, String htAccessFilePath) throws IOException {
    File workingDirectory = new File(System.getProperty("user.dir"));
    publicDirectoryFullPath = new File(workingDirectory, publicDirectory);
    File routesFile = new File(workingDirectory, routesFilePath);
    File htAccessFile = new File(workingDirectory, htAccessFilePath);
    createTemplates();
    routerMap = getDefaultHashMap();
    putPublicDirectoryFiles(publicDirectoryFullPath);
    putRoutes(routesFile);
    putRedirects(htAccessFile);
    return routerMap;
  }

  private void createTemplates() throws IOException {
    Templater templater = new Templater(publicDirectoryFullPath);
    templater.createTemplate(FOUR_HUNDRED_FOUR_TEMPLATE);
    templater.createTemplate(FILE_DIRECTORY_TEMPLATE);
    templater.createTemplate(FORM_TEMPLATE);
    templater.createTemplate(PARAMETERS_TEMPLATE);
  }

  public DefaultHashMap getDefaultHashMap() throws IOException {
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