package http;

import java.io.*;

public class Body {
  InputStream fileIn;
  ByteArrayOutputStream fileOut;

  public byte[] get(String baseURL, String queryString) throws IOException {
    if (queryString == null)
      return withNoQueryString(baseURL);
    else {
      String[] queryStringArray = parseQueryString(queryString);
      return withQueryString(baseURL, queryStringArray);
    }
  }

  public String[] parseQueryString(String queryString) {
    return queryString.split("=");
  }

  public byte[] withNoQueryString(String baseURL) throws IOException {
    fileIn = new FileInputStream(baseURL);
    fileOut = new ByteArrayOutputStream();
    int b;

    while ( ( b = fileIn.read() ) != -1 ) fileOut.write(b);
    return fileOut.toByteArray();
  }

  public byte[] withQueryString(String baseURL, String[] queryStringArray) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(baseURL));
    ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
    PrintWriter writer = new PrintWriter(fileOut);
    String line;

    while ((line = reader.readLine()) != null) writer.println(line.replaceAll(queryStringArray[0],queryStringArray[1]));

    writer.close();
    reader.close();
    return fileOut.toByteArray();
  }

}
