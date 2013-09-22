package http.response.httpMethod;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

public interface HttpMethod {
  public byte[] get(File routeFile, HashMap request) throws IOException, ParseException;
}
