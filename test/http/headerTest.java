package http;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.Date;

import static junit.framework.Assert.assertEquals;

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
                            + "\n" + new Date()
                            + "\n" + "Server: NinjaServer 1.0"
                            + "\n" + "Content-Type: text/html; charset = ISO-8859-1"
                            + "\n" + "Content-length: 107\n";

    assertEquals(header.header("localhost:5000/donaldduck.html", "GET", true, 107), expectedHeader);
  }

  @Test
  public void testHTMHeaderType() {
    String expectedHeader = "HTTP/1.1 200 OK"
        + "\n" + new Date()
        + "\n" + "Server: NinjaServer 1.0"
        + "\n" + "Content-Type: text/html; charset = ISO-8859-1"
        + "\n" + "Content-length: 107\n";

    assertEquals(header.header("localhost:5000/donaldduck.htm", "GET", true, 107), expectedHeader);
  }

  @Test
  public void testJPEGHeaderType() {
    String expectedHeader = "HTTP/1.1 200 OK"
        + "\n" + new Date()
        + "\n" + "Server: NinjaServer 1.0"
        + "\n" + "Content-Type: image/jpeg"
        + "\n" + "Content-length: 107\n";

    assertEquals(header.header("localhost:5000/donaldduck.jpeg", "GET", true, 107), expectedHeader);
  }

  @Test
  public void testJPGHeaderType() {
    String expectedHeader = "HTTP/1.1 200 OK"
        + "\n" + new Date()
        + "\n" + "Server: NinjaServer 1.0"
        + "\n" + "Content-Type: image/jpeg"
        + "\n" + "Content-length: 107\n";

    assertEquals(header.header("localhost:5000/donaldduck.jpg", "GET", true, 107), expectedHeader);
  }

  @Test
  public void testGIFHeaderType() {
    String expectedHeader = "HTTP/1.1 200 OK"
        + "\n" + new Date()
        + "\n" + "Server: NinjaServer 1.0"
        + "\n" + "Content-Type: image/gif"
        + "\n" + "Content-length: 107\n";

    assertEquals(header.header("localhost:5000/donaldduck.gif", "GET", true, 107), expectedHeader);
  }

  @Test
  public void testPNGHeaderType() {
     String expectedHeader = "HTTP/1.1 200 OK"
        + "\n" + new Date()
        + "\n" + "Server: NinjaServer 1.0"
        + "\n" + "Content-Type: image/png"
        + "\n" + "Content-length: 107\n";

    assertEquals(header.header("localhost:5000/donaldduck.png", "GET", true, 107), expectedHeader);
  }

  @Test
  public void test404HeaderType() {
    String expectedHeader = "HTTP/1.1 404 File Not Found"
        + "\n" + new Date()
        + "\n" + "Server: NinjaServer 1.0"
        + "\n" + "Content-Type: text/html; charset = ISO-8859-1"
        + "\n" + "Content-length: 107\n";

    assertEquals(header.header("localhost:5000/404.html", "GET", false, 107), expectedHeader);
  }
}