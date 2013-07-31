package http;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class RequestTest {

  private Request request;
  private String NEWLINE = "\r\n";

  @Before
  public void setUp() throws IOException {
    request = new Request();
  }

  @Test
  public void testParseGetRequestWithQueryString() {
    request.parseMessage("GET /donaldduck.html?text_color=blue HTTP/1.0");
    assertEquals(request.httpMethod, "GET");
    assertEquals(request.fullURL, "/donaldduck.html?text_color=blue");
    assertEquals(request.httpProtocol, "HTTP/1.0");
    assertEquals(request.baseURL, "/donaldduck.html");
    assertEquals(request.queryString, "text_color=blue");
  }

  @Test
  public void testParseRequestWithNoQueryString() {
    request.parseMessage("GET /donaldduck.html HTTP/1.0");
    assertEquals(request.httpMethod, "GET");
    assertEquals(request.fullURL, "/donaldduck.html");
    assertEquals(request.httpProtocol, "HTTP/1.0");
    assertEquals(request.baseURL, "/donaldduck.html");
    assertEquals(request.queryString, null);
  }

  @Test
  public void testParsePostRequestWithOneKeyValuePair() {
    String requestHeader =   "POST /color_picker_post.html HTTP/1.1\r\n"
        + "Host: localhost:5000\r\n"
        + "Connection: keep-alive\r\n"
        + "Content-Length: 15\r\n"
        + "Cache-Control: max-age=0\r\n"
        + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n"
        + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36\r\n"
        + "Accept-Encoding: gzip,deflate,sdch\r\n"
        + "Accept-Language: en-US,en;q=0.8\r\n"
        + "Cookie: textwrapon=false; wysiwyg=textarea\r\n";
    String requestBody     = "text_color=blue";
    request.parseMessage(requestHeader + NEWLINE + requestBody);
    assertEquals(request.queryString, "text_color=blue");
  }

}