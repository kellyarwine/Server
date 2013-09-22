package http.response.routeType;

import http.response.code.FourHundredFour;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

public class FileNotFound implements RouteType {
  public byte[] get(File routeFile, HashMap request) throws IOException, ParseException {
    FourHundredFour fourHundredFour = new FourHundredFour();
    return fourHundredFour.build(routeFile, request);
  }
}