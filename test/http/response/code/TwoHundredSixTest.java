package http.response.code;

import org.junit.Test;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import static junit.framework.Assert.assertEquals;

public class TwoHundredSixTest {
  private String NEW_LINE = "\r\n";

  @Test
  public void build() throws IOException, ParseException {
    HashMap<String, String> request = new HashMap<String, String>();
    request.put("httpMethod", "POST");
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
    request.put("Range", "bytes=500-599");
    request.put("queryString", "text_color=blue");
    String expectedHeader = "HTTP/1.1 206 Partial Content\r\n"
        + "Date: " + currentDateTime() + "\r\n"
        + "Server: NinjaServer 1.0" + "\r\n"
        + "Content-type: text/html; charset=UTF-8" + "\r\n"
        + "Content-length: 99\r\n"
        + "Content-Range: bytes 500-599/21552\r\n";
    String workingDirectory = System.getProperty("user.dir");
    File publicDirectoryFullPath = new File(workingDirectory, "test/public/");
    File routeFile = new File(publicDirectoryFullPath, (String)request.get("url"));
    byte[] fullExpectedBodyInBytes = toBytes(routeFile);
    byte[] expectedBodyInBytes = new byte[99];
    System.arraycopy(fullExpectedBodyInBytes, 500, expectedBodyInBytes, 0, 99);
    String expectedBody   = new String(expectedBodyInBytes);
    String expectedResult = expectedHeader + NEW_LINE + expectedBody;

    TwoHundredSix twoHundredSix = new TwoHundredSix();
    byte[] actualResultInBytes = twoHundredSix.build(routeFile, request);
    String actualResult = new String(actualResultInBytes);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void cobSpecTest() throws IOException, ParseException {
    HashMap<String, String> request = new HashMap<String, String>();
    request.put("httpMethod", "GET");
    request.put("url", "/partial_content.txt");
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
    request.put("Range", "bytes=0-4");
    String expectedHeader = "HTTP/1.1 206 Partial Content\r\n"
        + "Date: " + currentDateTime() + "\r\n"
        + "Server: NinjaServer 1.0" + "\r\n"
        + "Content-type: text/plain; charset=UTF-8" + "\r\n"
        + "Content-length: 4\r\n"
        + "Content-Range: bytes 0-4/76\r\n";
    String workingDirectory = System.getProperty("user.dir");
    File publicDirectoryFullPath = new File(workingDirectory, "test/public/");
    File routeFile = new File(publicDirectoryFullPath, (String)request.get("url"));
    byte[] fullExpectedBodyInBytes = toBytes(routeFile);
    byte[] expectedBodyInBytes = new byte[4];
    System.arraycopy(fullExpectedBodyInBytes, 0, expectedBodyInBytes, 0, 4);
    String expectedBody   = new String(expectedBodyInBytes);
    String expectedResult = expectedHeader + NEW_LINE + expectedBody;

    TwoHundredSix twoHundredSix = new TwoHundredSix();
    byte[] actualResultInBytes = twoHundredSix.build(routeFile, request);
    String actualResult = new String(actualResultInBytes);

    assertEquals(expectedResult, actualResult);
  }

  public byte[] toBytes(File routeFile) throws IOException {
    InputStream inputStream = new FileInputStream(routeFile);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    int chr;

    while ((chr = inputStream.read()) != -1)
      outputStream.write(chr);

    return outputStream.toByteArray();
  }

  private String currentDateTime() throws ParseException {
    Date unformattedDateTime = Calendar.getInstance().getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    return sdf.format(unformattedDateTime);
  }
}