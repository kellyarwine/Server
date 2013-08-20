package http.response.routeType;

import http.response.code.FourHundredFour;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class FileNotFound implements RouteType {
  public byte[] get(File routeFile, HashMap request) throws IOException {
    FourHundredFour fourHundredFour = new FourHundredFour();
    return fourHundredFour.build(routeFile, request);
  }
}