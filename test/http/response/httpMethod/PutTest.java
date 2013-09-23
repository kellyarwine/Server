package http.response.httpMethod;

import http.server.Templater;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class PutTest {
  private String NEW_LINE = "\r\n";
  File workingDirectory;
  File publicDirectoryFullPath;
  Templater templater;

  @Before
  public void setUp() throws IOException, URISyntaxException {
    workingDirectory = new File(System.getProperty("user.dir"));
    publicDirectoryFullPath = new File(workingDirectory, "test/public/");
    new Templater().copyTemplatesToDisk("/http/templates/templates.zip", publicDirectoryFullPath);
  }

  @After
  public void tearDown() {
    deleteDirectory(new File(publicDirectoryFullPath, "/templates"));
  }

  @Test
  public void postRequestToTemplate() throws IOException, ParseException {
    HashMap request = new HashMap();
    request.put("httpMethod", "POST");
    request.put("url", "/form");
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
    File routeFile = new File(publicDirectoryFullPath, "templates/form.html");
    Put put = new Put();
    byte[] actualResultInBytes = put.get(routeFile, request);
    String actualResult = new String(actualResultInBytes);

    String expectedResult  = "The value of data = 1";
    String expectedResult2 = "The value of data_2 = 2";
    String expectedResult3 = "The value of data_3 = 3";

    assertTrue(actualResult.contains(expectedResult));
    assertTrue(actualResult.contains(expectedResult2));
    assertTrue(actualResult.contains(expectedResult3));
  }

  @Test
  public void postRequestToNonTemplate() throws IOException, ParseException {
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
    Put put = new Put();
    byte[] actualResultInBytes = put.get(routeFile, request);
    String actualResult = new String(actualResultInBytes);

    String expectedResult = "HTTP/1.1 405 Method Not Allowed\r\n"
        + "Date: " + currentDateTime() + "\r\n"
        +"Server: NinjaServer 1.0\r\n"
        +"Content-type: image/jpeg\r\n"
        +"Content-length: 0\r\n"
        +"Allow: GET\r\n"
        + NEW_LINE;

    assertEquals(expectedResult, actualResult);
  }

  private String currentDateTime() throws ParseException {
    Date unformattedDateTime = Calendar.getInstance().getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    return sdf.format(unformattedDateTime);
  }

  private void deleteDirectory(File directory) {
    if (directory.isDirectory()) {
      String[] children = directory.list();
      for (int i=0; i<children.length; i++) {
        deleteDirectory(new File(directory, children[i]));
      }
    }
    directory.delete();
  }
}