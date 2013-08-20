package http.response.httpMethod;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class PostTest {
  private String NEW_LINE = "\r\n";

  @Test
  public void postRequestToTemplate() throws IOException {
    HashMap request = new HashMap();
    request.put("httpMethod", "POST");
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
    Post post = new Post();
    byte[] actualResultInBytes = post.get(routeFile, request);
    String actualResult = new String(actualResultInBytes);

    String expectedResult  = "Bye, someone!";

    assertTrue(actualResult.contains(expectedResult));
  }

  @Test
  public void postRequestToNonTemplate() throws IOException {
    HashMap request = new HashMap();
    request.put("httpMethod", "POST");
    request.put("url", "/punky_brewster.jpg");
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
    Post post = new Post();
    byte[] actualResultInBytes = post.get(routeFile, request);
    String actualResult = new String(actualResultInBytes);

    String expectedResult = "HTTP/1.1 405 Method Not Allowed\r\n"
        + new Date() + "\r\n"
        +"Server: NinjaServer 1.0\r\n"
        +"Content-type: image/jpeg\r\n"
        +"Content-length: 0\r\n"
        +"Allow: GET\r\n"
        + NEW_LINE;

    assertEquals(expectedResult, actualResult);
  }
}