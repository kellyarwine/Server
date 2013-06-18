package http;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class HeaderTest {

  private Header header;

  @Before
  public void setUp() throws IOException {
    header = new Header();
  }

  @Test
  public void testHTMLHeaderType() {
    String expectedHeader = "HTTP/1.1 200 OK"
        + "\r\n" + new Date()
        + "\r\n" + "Server: NinjaServer 1.0"
        + "\r\n" + "Content-Type: text/html; charset=UTF-8"
        + "\r\n" + "Content-length: 107";

    assertEquals(header.get("localhost:5000/donaldduck.html", "GET", 107), expectedHeader);
  }

  @Test
  public void testHTMHeaderType() {
    String expectedHeader = "HTTP/1.1 200 OK"
        + "\r\n" + new Date()
        + "\r\n" + "Server: NinjaServer 1.0"
        + "\r\n" + "Content-Type: text/html; charset=UTF-8"
        + "\r\n" + "Content-length: 107";

    assertEquals(header.get("localhost:5000/donaldduck.htm", "GET", 107), expectedHeader);
  }

  @Test
  public void testJPEGHeaderType() {
    String expectedHeader = "HTTP/1.1 200 OK"
        + "\r\n" + new Date()
        + "\r\n" + "Server: NinjaServer 1.0"
        + "\r\n" + "Content-Type: image/jpeg"
        + "\r\n" + "Content-length: 107";

    assertEquals(header.get("localhost:5000/donaldduck.jpeg", "GET", 107), expectedHeader);
  }

  @Test
  public void testJPGHeaderType() {
    String expectedHeader = "HTTP/1.1 200 OK"
        + "\r\n" + new Date()
        + "\r\n" + "Server: NinjaServer 1.0"
        + "\r\n" + "Content-Type: image/jpeg"
        + "\r\n" + "Content-length: 107";

    assertEquals(header.get("localhost:5000/donaldduck.jpg", "GET", 107), expectedHeader);
  }

  @Test
  public void testGIFHeaderType() {
    String expectedHeader = "HTTP/1.1 200 OK"
        + "\r\n" + new Date()
        + "\r\n" + "Server: NinjaServer 1.0"
        + "\r\n" + "Content-Type: image/gif"
        + "\r\n" + "Content-length: 107";

    assertEquals(header.get("localhost:5000/donaldduck.gif", "GET", 107), expectedHeader);
  }

  @Test
  public void testPNGHeaderType() {
    String expectedHeader = "HTTP/1.1 200 OK"
        + "\r\n" + new Date()
        + "\r\n" + "Server: NinjaServer 1.0"
        + "\r\n" + "Content-Type: image/png"
        + "\r\n" + "Content-length: 107";

    assertEquals(header.get("localhost:5000/donaldduck.png", "GET", 107), expectedHeader);
  }

  @Test
  public void test404HeaderType() {
    String expectedHeader = "HTTP/1.1 404 File Not Found"
        + "\r\n" + new Date()
        + "\r\n" + "Server: NinjaServer 1.0"
        + "\r\n" + "Content-Type: text/html; charset=UTF-8"
        + "\r\n" + "Content-length: 107";

    assertEquals(header.get("localhost:5000/404.html", "GET", 107), expectedHeader);
  }
}