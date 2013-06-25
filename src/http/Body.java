package http;

import java.io.*;

public class Body {
  public String bodyString;
  public byte[] bodyInBytes;

  public byte[] get(String route, String queryString) throws IOException {
    readFileToBody(route);
    handleQueryString(queryString);
    return bodyInBytes;
  }

  private void handleQueryString(String queryString) throws IOException {
    if (queryString != null) {
      convertBodyToString();
      updateBodyWithQueryString(queryString);
      convertBodyToBytes();
    }
  }

  public void readFileToBody(String route) throws IOException {
    InputStream fileIn = new FileInputStream(route);
    ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
    int b;

    while ( ( b = fileIn.read() ) != -1 ) fileOut.write(b);

    bodyInBytes = fileOut.toByteArray();
  }

  public void convertBodyToString() throws IOException {
    bodyString = new String(bodyInBytes);
  }

  public void updateBodyWithQueryString(String queryString) {
    String[] queryStringArray = parseQueryString(queryString);
    findAndReplace(queryStringArray[0], queryStringArray[1]);
  }

  public String[] parseQueryString(String queryString) {
    return queryString.split("=");
  }

  public void findAndReplace(String key, String value) {
    bodyString = bodyString.replaceAll(key, value);
  }

  public void convertBodyToBytes() {
    bodyInBytes = bodyString.getBytes();
  }

}