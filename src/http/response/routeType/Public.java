package http.response.routeType;

import http.response.httpMethod.HttpMethod;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

public class Public implements RouteType {
  private HashMap httpMethodMap;

  public Public() {
    httpMethodMap = new HttpMethodMapBuilder().build();
  }

  public byte[] get(File routeFile, HashMap request) throws IOException, ParseException {
    HttpMethod httpMethod = (HttpMethod)httpMethodMap.get(request.get("httpMethod"));
    return httpMethod.get(routeFile, request);
  }
}