package http.response.httpMethod;

import org.junit.Test;

import java.io.*;
import java.util.Date;
import java.util.HashMap;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

public class GetTest {
  private String NEW_LINE = "\r\n";

  @Test
  public void getRequestToTemplate() throws IOException {
    HashMap request = new HashMap();
    request.put("httpMethod", "GET");
    request.put("url", "/hi_everyone.html");
    request.put("httpProtocol", "HTTP/1.1");
    request.put("Host", "localhost:5000");
    request.put("Connection", "keep-alive");
    request.put("Content-Length", "15");
    request.put("Cache-Control", "max-age=0");
    request.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
    request.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36");
    request.put("Accept-Encoding", "gzip,deflate,sdch");
    request.put("Accept-Language", "en-US,en;q=0.8");
    request.put("Cookie", "textwrapon=false; wysiwyg=textarea");
    request.put("queryString", "Hi=Bye&everyone=someone");
    String workingDirectory = System.getProperty("user.dir");
    File publicDirectoryFullPath = new File(workingDirectory, "test/public/");
    File routeFile = new File(publicDirectoryFullPath, (String)request.get("url"));
    Get get = new Get();
    byte[] actualResultInBytes = get.get(routeFile, request);
    String actualResult = new String(actualResultInBytes);

    String expectedResult  = "Bye, someone!";

    assertTrue(actualResult.contains(expectedResult));
  }

  @Test
  public void getRequestToNonTemplate() throws IOException {
    HashMap request = new HashMap();
    request.put("httpMethod", "GET");
    request.put("url", "/the_goal.html");
    request.put("httpProtocol", "HTTP/1.1");
    request.put("Host", "localhost:5000");
    request.put("Connection", "keep-alive");
    request.put("Content-Length", "15");
    request.put("Cache-Control", "max-age=0");
    request.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
    request.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36");
    request.put("Accept-Encoding", "gzip,deflate,sdch");
    request.put("Accept-Language", "en-US,en;q=0.8");
    request.put("Cookie", "textwrapon=false; wysiwyg=textarea");
    request.put("queryString", "data=1&data_2=2&data_3=3");
    String workingDirectory = System.getProperty("user.dir");
    File publicDirectoryFullPath = new File(workingDirectory, "test/public/");
    File routeFile = new File(publicDirectoryFullPath, (String)request.get("url"));
    Get get = new Get();
    byte[] actualResult = get.get(routeFile, request);

    String expectedHeaderString = "HTTP/1.1 200 OK\r\n"
                                + new Date() + "\r\n"
                                +"Server: NinjaServer 1.0\r\n"
                                +"Content-type: text/html; charset=UTF-8\r\n"
                                +"Content-length: 21552\r\n";
    byte[] expectedHeader = expectedHeaderString.getBytes();
    byte[] expectedBody = toBytes(routeFile);
    byte[] expectedResult = concatenate(new byte[][]{ expectedHeader, NEW_LINE.getBytes(), expectedBody });

    assertArrayEquals(expectedResult, actualResult);
  }

  @Test
  public void getRequestForPartialContent() throws IOException {
    HashMap request = new HashMap();
    request.put("httpMethod", "GET");
    request.put("url", "/the_goal.html");
    request.put("httpProtocol", "HTTP/1.1");
    request.put("Host", "localhost:5000");
    request.put("Connection", "keep-alive");
    request.put("Content-Length", "15");
    request.put("Cache-Control", "max-age=0");
    request.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
    request.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36");
    request.put("Accept-Encoding", "gzip,deflate,sdch");
    request.put("Accept-Language", "en-US,en;q=0.8");
    request.put("Cookie", "textwrapon=false; wysiwyg=textarea");
    request.put("Range", "bytes=500-999");
    request.put("queryString", "data=1&data_2=2&data_3=3");
    String workingDirectory = System.getProperty("user.dir");
    File publicDirectoryFullPath = new File(workingDirectory, "test/public/");
    File routeFile = new File(publicDirectoryFullPath, (String)request.get("url"));
    Get get = new Get();
    byte[] actualResult = get.get(routeFile, request);

    String expectedHeaderString = "HTTP/1.1 206 Partial Content\r\n"
        + new Date() + "\r\n"
        + "Server: NinjaServer 1.0" + "\r\n"
        + "Content-type: text/html; charset=UTF-8" + "\r\n"
        + "Content-length: 499\r\n"
        + "Content-Range: bytes 500-999/21552\r\n";
    byte[] expectedHeader = expectedHeaderString.getBytes();
    byte[] fullExpectedBodyInBytes = toBytes(routeFile);
    byte[] expectedBody = new byte[499];
    System.arraycopy(fullExpectedBodyInBytes, 500, expectedBody, 0, 499);
    byte[] expectedResult = concatenate(new byte[][] {expectedHeader, NEW_LINE.getBytes(), expectedBody});

    assertArrayEquals(expectedResult, actualResult);
  }

  public byte[] toBytes(File routeFile) throws IOException {
    InputStream inputStream = new FileInputStream(routeFile);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    int chr;

    while ((chr = inputStream.read()) != -1)
      outputStream.write(chr);

    return outputStream.toByteArray();
  }

  public byte[] concatenate(byte[][] byteArray) throws IOException {
    ByteArrayOutputStream bOutput = new ByteArrayOutputStream();
    for(int i=0; i<byteArray.length; i++) {
      bOutput.write(byteArray[i]);
    }
    return bOutput.toByteArray();
  }
}