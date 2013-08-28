package http.request;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class ParserTest {
  private Parser parser;
  private String NEWLINE = "\r\n";

  @Before
  public void setUp() throws IOException {
    parser = new Parser();
  }

  @Test
  public void testSmallGetRequestWithNoQueryString() {
    String request      = "GET /donaldduck.html HTTP/1.0\r\n"
                        + "Host: localhost:5000\r\n"
                        + NEWLINE;
    HashMap requestHash = parser.parse(request);

    assertEquals("GET", requestHash.get("httpMethod"));
    assertEquals("/donaldduck.html", requestHash.get("url"));
    assertEquals("HTTP/1.0", requestHash.get("httpProtocol"));
    assertEquals("localhost:5000", requestHash.get("Host"));
    assertEquals(null, requestHash.get("originalQueryString"));
  }

  @Test
  public void testSmallGetRequestWithQueryString() {
    String request  = "GET /donaldduck.html?text_color=blue HTTP/1.0\r\n"
                    + "Host: localhost:5000\r\n"
                    + NEWLINE;
    HashMap requestHash = parser.parse(request);

    assertEquals("GET", requestHash.get("httpMethod"));
    assertEquals("/donaldduck.html", requestHash.get("url"));
    assertEquals("HTTP/1.0", requestHash.get("httpProtocol"));
    assertEquals("localhost:5000", requestHash.get("Host"));
    assertEquals(null, requestHash.get("originalQueryString"));
  }

  @Test
  public void testPostRequestWithOneKeyValuePair() {
    String requestHeader  = "POST /color_picker_post.html HTTP/1.1\r\n"
                          + "Host: localhost:5000\r\n"
                          + "Connection: keep-alive\r\n"
                          + "Content-Length: 15\r\n"
                          + "Cache-Control: max-age=0\r\n"
                          + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n"
                          + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36\r\n"
                          + "Accept-Encoding: gzip,deflate,sdch\r\n"
                          + "Accept-Language: en-US,en;q=0.8\r\n"
                          + "Cookie: textwrapon=false; wysiwyg=textarea\r\n";
    String requestBody    = "text_color=blue";
    String request = requestHeader + NEWLINE + requestBody;
    HashMap requestHash = parser.parse(request);

    assertEquals("POST", requestHash.get("httpMethod"));
    assertEquals("/color_picker_post.html", requestHash.get("url"));
    assertEquals("HTTP/1.1", requestHash.get("httpProtocol"));
    assertEquals("localhost:5000", requestHash.get("Host"));
    assertEquals("keep-alive", requestHash.get("Connection"));
    assertEquals("15", requestHash.get("Content-Length"));
    assertEquals("max-age=0", requestHash.get("Cache-Control"));
    assertEquals("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8", requestHash.get("Accept"));
    assertEquals("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36", requestHash.get("User-Agent"));
    assertEquals("gzip,deflate,sdch", requestHash.get("Accept-Encoding"));
    assertEquals("en-US,en;q=0.8", requestHash.get("Accept-Language"));
    assertEquals("textwrapon=false; wysiwyg=textarea", requestHash.get("Cookie"));
    assertEquals(requestBody, requestHash.get("queryString"));
  }

  @Test
  public void testPostRequestWithThreeKeyValuePairs() {
    String requestHeader  = "POST /color_picker_post.html HTTP/1.1\r\n"
                          + "Host: localhost:5000\r\n"
                          + "Connection: keep-alive\r\n"
                          + "Content-Length: 15\r\n"
                          + "Cache-Control: max-age=0\r\n"
                          + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n"
                          + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36\r\n"
                          + "Accept-Encoding: gzip,deflate,sdch\r\n"
                          + "Accept-Language: en-US,en;q=0.8\r\n"
                          + "Cookie: textwrapon=false; wysiwyg=textarea\r\n";
    String requestBody    = "text_color1=blue&text_color2=red&text_color3=yellow";
    String request = requestHeader + NEWLINE + requestBody;
    HashMap requestHash = parser.parse(request);

    assertEquals(requestHash.get("httpMethod"), "POST");
    assertEquals("/color_picker_post.html", requestHash.get("url"));
    assertEquals("HTTP/1.1", requestHash.get("httpProtocol"));
    assertEquals("localhost:5000", requestHash.get("Host"));
    assertEquals("keep-alive", requestHash.get("Connection"));
    assertEquals("15", requestHash.get("Content-Length"));
    assertEquals("max-age=0", requestHash.get("Cache-Control"));
    assertEquals("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8", requestHash.get("Accept"));
    assertEquals("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36", requestHash.get("User-Agent"));
    assertEquals("gzip,deflate,sdch", requestHash.get("Accept-Encoding"));
    assertEquals("en-US,en;q=0.8", requestHash.get("Accept-Language"));
    assertEquals("textwrapon=false; wysiwyg=textarea", requestHash.get("Cookie"));
    assertEquals(requestBody, requestHash.get("queryString"));
  }

  @Test
  public void test() {
    String[] array = "Range: : bytes=0-4".split(":");
  }
}