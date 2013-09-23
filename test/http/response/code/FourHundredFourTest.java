package http.response.code;

import http.server.Templater;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import static junit.framework.Assert.assertEquals;

public class FourHundredFourTest {
  private String NEW_LINE = "\r\n";
  private File publicDirectoryFullPath;

  @Before
  public void setUp() throws IOException {
    File workingDirectory = new File(System.getProperty("user.dir"));
    publicDirectoryFullPath = new File(workingDirectory, "test/public/");
    new Templater().copyTemplatesToDisk("/http/templates/templates.zip", publicDirectoryFullPath);
  }

  @After
  public void tearDown() {
    deleteDirectory(new File(publicDirectoryFullPath, "/templates"));
  }

  @Test
  public void build() throws IOException, ParseException {
    HashMap request = new HashMap();
    request.put("httpMethod", "POST");
    request.put("url", "/templates/404.html");
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

    File routeFile = new File(publicDirectoryFullPath, (String)request.get("url"));
    FourHundredFour fourHundredFour = new FourHundredFour();
    byte[] actualResultInBytes = fourHundredFour.build(routeFile, request);
    String actualResult = new String(actualResultInBytes);

    String expectedHeader = "HTTP/1.1 404 File Not Found\r\n"
        + "Date: " + currentDateTime() + "\r\n"
        + "Server: NinjaServer 1.0" + "\r\n"
        + "Content-type: text/html; charset=UTF-8" + "\r\n"
        + "Content-length: 127\r\n";
    String expectedBody   = "<html>\n" +
                            "<head>\n" +
                            "    <title>Page not found</title>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "  Your page cannot be found.  Please try again.\n" +
                            "</body>\n" +
                            "</html>\n";
    String expectedResult = expectedHeader + NEW_LINE + expectedBody;

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

  public void deleteDirectory(File directory) {
    if (directory.isDirectory()) {
      String[] children = directory.list();
      for (int i=0; i<children.length; i++) {
        deleteDirectory(new File(directory, children[i]));
      }
    }
    directory.delete();
  }

  private String currentDateTime() throws ParseException {
    Date unformattedDateTime = Calendar.getInstance().getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    return sdf.format(unformattedDateTime);
  }
}