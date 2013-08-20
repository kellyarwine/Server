package http.response.code;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class ResponseHeaderTest {
  private ResponseHeader responseHeader;

  @Before
  public void setUp() throws IOException {
    responseHeader = new ResponseHeader();
  }

  @Test
  public void htmlFile() {
    String expectedResult = "HTTP/1.1 200 OK\r\n"
                          + new Date() + "\r\n"
                          + "Server: NinjaServer 1.0" + "\r\n"
                          + "Content-type: text/html; charset=UTF-8" + "\r\n"
                          + "Content-length: 107" + "\r\n";
    File routeFile = new File("localhost:5000/donaldduck.html");
    String actualResult = new String(responseHeader.build(routeFile, "200 OK", 107));
    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void htmFile() {
    String expectedResult = "HTTP/1.1 200 OK\r\n"
                                + new Date() + "\r\n"
                                + "Server: NinjaServer 1.0" + "\r\n"
                                + "Content-type: text/html; charset=UTF-8" + "\r\n"
                                + "Content-length: 107" + "\r\n";
    File routeFile = new File("localhost:5000/donaldduck.htm");
    String actualResult = new String(responseHeader.build(routeFile, "200 OK", 107));
    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void jpegFile() {
    String expectedResult = "HTTP/1.1 200 OK\r\n"
                          + new Date() + "\r\n"
                          + "Server: NinjaServer 1.0" + "\r\n"
                          + "Content-type: image/jpeg" + "\r\n"
                          + "Content-length: 107" + "\r\n";
    File routeFile = new File("localhost:5000/donaldduck.jpeg");
    String actualResult = new String(responseHeader.build(routeFile, "200 OK", 107));
    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void jpgFile() {
    String expectedResult = "HTTP/1.1 200 OK\r\n"
                          + new Date() + "\r\n"
                          + "Server: NinjaServer 1.0" + "\r\n"
                          + "Content-type: image/jpeg" + "\r\n"
                          + "Content-length: 107" + "\r\n";
    File routeFile = new File("localhost:5000/donaldduck.jpg");
    String actualResult = new String(responseHeader.build(routeFile, "200 OK", 107));
    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void gifFile() {
    String expectedResult = "HTTP/1.1 200 OK\r\n"
                          + new Date() + "\r\n"
                          + "Server: NinjaServer 1.0" + "\r\n"
                          + "Content-type: image/gif" + "\r\n"
                          + "Content-length: 107" + "\r\n";
    File routeFile = new File("localhost:5000/donaldduck.gif");
    String actualResult = new String(responseHeader.build(routeFile, "200 OK", 107));
    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void pngFile() {
    String expectedResult = "HTTP/1.1 200 OK\r\n"
                          + new Date() + "\r\n"
                          + "Server: NinjaServer 1.0" + "\r\n"
                          + "Content-type: image/png" + "\r\n"
                          + "Content-length: 107" + "\r\n";
    File routeFile = new File("localhost:5000/donaldduck.png");
    String actualResult = new String(responseHeader.build(routeFile, "200 OK", 107));
    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void fourHundredFourFile() {
    String expectedResult = "HTTP/1.1 404 File Not Found\r\n"
                          + new Date() + "\r\n"
                          + "Server: NinjaServer 1.0" + "\r\n"
                          + "Content-type: text/html; charset=UTF-8" + "\r\n"
                          + "Content-length: 107" + "\r\n";
    File routeFile = new File("localhost:5000/404.html");
    String actualResult = new String(responseHeader.build(routeFile, "404 File Not Found", 107));
    assertEquals(expectedResult, actualResult);
  }
}