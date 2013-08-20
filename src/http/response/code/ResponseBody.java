package http.response.code;

import java.io.*;

public class ResponseBody {

  public byte[] build(File routeFile) throws IOException {
      return toBytes(routeFile);
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