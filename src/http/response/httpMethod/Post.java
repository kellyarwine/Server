package http.response.httpMethod;

import http.response.code.Code;

import java.io.*;
import java.util.HashMap;

public class Post implements HttpMethod {
  private HashMap templateMap;

  public Post() {
    templateMap = new TemplateMapBuilder().build();
  }

  public byte[] get(File routeFile, HashMap request) throws IOException {
    Code code = (Code) templateMap.get(isTemplate(routeFile));
    return code.build(routeFile, request);
  }

  private boolean isTemplate(File routeFile) throws IOException {
    String routeText = new String(toBytes(routeFile));
    return routeText.contains("{ %") && routeText.toString().contains("% }") ;
  }

  private byte[] toBytes(File routeFile) throws IOException {
    InputStream inputStream = new FileInputStream(routeFile);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    int chr;

    while ((chr = inputStream.read()) != -1)
      outputStream.write(chr);

    return outputStream.toByteArray();
  }
}