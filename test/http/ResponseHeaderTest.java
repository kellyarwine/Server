package http;

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
  public void testHTML() {
    String expectedHeaderString = "HTTP/1.1 200 OK"
        + "\r\n" + new Date()
        + "\r\n" + "Server: NinjaServer 1.0"
        + "\r\n" + "Content-type: text/html; charset=UTF-8"
        + "\r\n" + "Content-length: 107"
        + "\r\n\r\n";
    File routeFile = new File("localhost:5000/donaldduck.html");
    String actualHeaderString = getActualHeaderString(responseHeader.get(routeFile, "GET", 107));
    assertEquals(expectedHeaderString, actualHeaderString);
  }

  @Test
  public void testHTM() {
    String expectedHeaderString = "HTTP/1.1 200 OK"
        + "\r\n" + new Date()
        + "\r\n" + "Server: NinjaServer 1.0"
        + "\r\n" + "Content-type: text/html; charset=UTF-8"
        + "\r\n" + "Content-length: 107"
        + "\r\n\r\n";
    File routeFile = new File("localhost:5000/donaldduck.htm");
    String actualHeaderString = getActualHeaderString(responseHeader.get(routeFile, "GET", 107));
    assertEquals(expectedHeaderString, actualHeaderString);
  }

  @Test
  public void testJPEG() {
    String expectedHeaderString = "HTTP/1.1 200 OK"
        + "\r\n" + new Date()
        + "\r\n" + "Server: NinjaServer 1.0"
        + "\r\n" + "Content-type: image/jpeg"
        + "\r\n" + "Content-length: 107"
        + "\r\n\r\n";
    File routeFile = new File("localhost:5000/donaldduck.jpeg");
    String actualHeaderString = getActualHeaderString(responseHeader.get(routeFile, "GET", 107));
    assertEquals(expectedHeaderString, actualHeaderString);
  }

  @Test
  public void testJPG() {
    String expectedHeaderString = "HTTP/1.1 200 OK"
        + "\r\n" + new Date()
        + "\r\n" + "Server: NinjaServer 1.0"
        + "\r\n" + "Content-type: image/jpeg"
        + "\r\n" + "Content-length: 107"
        + "\r\n\r\n";
    File routeFile = new File("localhost:5000/donaldduck.jpg");
    String actualHeaderString = getActualHeaderString(responseHeader.get(routeFile, "GET", 107));
    assertEquals(expectedHeaderString, actualHeaderString);
  }

  @Test
  public void testGIF() {
    String expectedHeaderString = "HTTP/1.1 200 OK"
        + "\r\n" + new Date()
        + "\r\n" + "Server: NinjaServer 1.0"
        + "\r\n" + "Content-type: image/gif"
        + "\r\n" + "Content-length: 107"
        + "\r\n\r\n";
    File routeFile = new File("localhost:5000/donaldduck.gif");
    String actualHeaderString = getActualHeaderString(responseHeader.get(routeFile, "GET", 107));
    assertEquals(expectedHeaderString, actualHeaderString);
  }

  @Test
  public void testPNG() {
    String expectedHeaderString = "HTTP/1.1 200 OK"
        + "\r\n" + new Date()
        + "\r\n" + "Server: NinjaServer 1.0"
        + "\r\n" + "Content-type: image/png"
        + "\r\n" + "Content-length: 107"
        + "\r\n\r\n";
    File routeFile = new File("localhost:5000/donaldduck.png");
    String actualHeaderString = getActualHeaderString(responseHeader.get(routeFile, "GET", 107));
    assertEquals(expectedHeaderString, actualHeaderString);
  }

  @Test
  public void test404() {
    String expectedHeaderString = "HTTP/1.1 404 File Not Found"
        + "\r\n" + new Date()
        + "\r\n" + "Server: NinjaServer 1.0"
        + "\r\n" + "Content-type: text/html; charset=UTF-8"
        + "\r\n" + "Content-length: 107"
        + "\r\n\r\n";
    File routeFile = new File("localhost:5000/404.html");
    String actualHeaderString = getActualHeaderString(responseHeader.get(routeFile, "GET", 107));
    assertEquals(expectedHeaderString, actualHeaderString);
  }

  private String getActualHeaderString(byte[] actualHeader) {
    return new String(actualHeader);
  }
}