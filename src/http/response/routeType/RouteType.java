package http.response.routeType;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

public interface RouteType {
  public byte[] get(File routeFile, HashMap request) throws IOException, ParseException;
}
