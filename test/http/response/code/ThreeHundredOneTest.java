package http.response.code;

import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import static junit.framework.Assert.assertEquals;

public class ThreeHundredOneTest {
  private String NEW_LINE = "\r\n";

  @Test
  public void build() throws IOException {
    HashMap request = new HashMap();
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
    request.put("queryString", "text_color=blue");

    String expectedHeader = "HTTP/1.1 301 Moved Permanently\r\n"
                          + new Date() + "\r\n"
                          + "Server: NinjaServer 1.0" + "\r\n"
                          + "Content-type: text/html; charset=UTF-8" + "\r\n"
                          + "Content-length: 0\r\n"
                          + "Location: http://localhost:5000/the_goal.html\r\n";
    String expectedBody   = "";
    String expectedResult = expectedHeader + NEW_LINE + expectedBody;

    String workingDirectory = System.getProperty("user.dir");
    File publicDirectoryFullPath = new File(workingDirectory, "test/public/");
    File routeFile = new File(publicDirectoryFullPath, (String)request.get("url"));
    ThreeHundredOne threeHundredOne = new ThreeHundredOne();
    byte[] actualResultInBytes = threeHundredOne.build(routeFile, request);
    String actualResult = new String(actualResultInBytes);

    assertEquals(expectedResult, actualResult);
  }
}