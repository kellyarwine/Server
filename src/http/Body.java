package http;

import java.io.*;

public class Body {

  public String get(String baseURL, String queryString) throws IOException {
    String stringBody = readFile(baseURL);
    stringBody = handleQueryString(queryString, stringBody);

    return stringBody;
  }

  public String readFile(String baseURL) throws IOException {
    InputStream fileIn = new FileInputStream(baseURL);
    ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
    int b;

    while ( ( b = fileIn.read() ) != -1 ) fileOut.write(b);
    return fileOut.toString();
  }

  public String handleQueryString(String queryString, String stringBody) {
    String updatedBody = null;

    if (queryString != null) {
      String[] queryStringArray = parseQueryString(queryString);
      String key = queryStringArray[0];
      String value = queryStringArray[1];
      updatedBody = stringBody.replace(key, value);
    }
    else updatedBody = stringBody;

    return updatedBody;
  }

  public String[] parseQueryString(String queryString) {
    return queryString.split("=");
  }

  public int contentLengthInBytes(String content) {
    return content.getBytes().length;
  }

}