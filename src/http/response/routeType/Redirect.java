package http.response.routeType;

import http.response.code.ThreeHundredOne;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

public class Redirect implements RouteType {
  public byte[] get(File routeFile, HashMap request) throws IOException, ParseException {
    ThreeHundredOne threeHundredOne = new ThreeHundredOne();
    return threeHundredOne.build(routeFile, request);
  }
}