package http;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

public interface SystemRouter {
  public void getRouterMap(HashMap<String, String> serverConfig) throws IOException;
  public byte[] getResponse(HashMap request) throws IOException, ParseException;
  public String getRoute(HashMap request)throws IOException, ParseException;
}
