package http;

import java.io.*;

public class ResponseBody {
  private File publicDirectoryFullPath;
  private Templater templater;
  public String bodyString;
  public byte[] bodyInBytes;

  public ResponseBody(File publicDirectoryFullPath) {
    this.publicDirectoryFullPath = publicDirectoryFullPath;
    templater = new Templater(publicDirectoryFullPath);
  }

  public byte[] get(File routeFile, String queryString) throws IOException {
    if (routeFile.isFile()) {
      readFileToBodyInBytes(routeFile);
      handleQueryString(queryString);
    }
    else {
      bodyString = templater.buildTemplate(routeFile);
      convertBodyStringToBodyInBytes();
    }

    return bodyInBytes;
  }

  private void handleQueryString(String queryString) throws IOException {
    if (queryString != null) {
      readBodyInBytesToBodyString();
      updateBodyStringWithQueryString(queryString);
      convertBodyStringToBodyInBytes();
    }
  }

  private void readBodyInBytesToBodyString() {
    bodyString = new String(bodyInBytes);
  }

  private void readFileToBodyInBytes(File routeFile) throws IOException {
    InputStream inputStream = new FileInputStream(routeFile);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    int chr;

    while ((chr = inputStream.read()) != -1)
      outputStream.write(chr);

    bodyInBytes = outputStream.toByteArray();
  }

  public String[] parseQueryString(String queryString) {
    return queryString.split("&");
  }

  private void updateBodyStringWithQueryString(String queryString) {
    String[] keyValuePairs = parseQueryString(queryString);
    for(int i=0; i<keyValuePairs.length; i++) {
      String[] keyValuePair = parseKeyValuePairs(keyValuePairs[i]);
      String key = keyValuePair[0];
      String value = keyValuePair[1];
      findAndReplaceInBodyString(key, value);
    }
  }

  public String[] parseKeyValuePairs(String keyValuePair) {
    return keyValuePair.split("=");
  }

  private void findAndReplaceInBodyString(String key, String value) {
    bodyString = bodyString.replaceAll(key, value);
  }

  private void convertBodyStringToBodyInBytes() {
    bodyInBytes = bodyString.getBytes();
  }
}