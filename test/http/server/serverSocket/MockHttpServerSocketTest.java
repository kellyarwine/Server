package http.server.serverSocket;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;

public class MockHttpServerSocketTest {
  private String NEW_LINE = "\r\n";
  private HttpServerSocket httpServerSocket;

  @Before
  public void setUp() throws IOException {
    ArrayList requests = new ArrayList();
    requests.add(simpleRootRequest());
    httpServerSocket = new MockHttpServerSocket(requests);
  }

  @Test
  public void close() throws IOException {
    httpServerSocket.close();
    assertTrue(httpServerSocket.isClosed());
  }

  @Test
  public void bound() throws IOException {
    assertFalse(httpServerSocket.isBound());
  }

  private String simpleRootRequest() {
    String requestHeader =
        "GET / HTTP/1.1\r\n"
            + "Host: localhost:5000\r\n"
            + "Connection: keep-alive\r\n"
            + "Content-Length: 15\r\n"
            + "Cache-Control: max-age=0\r\n"
            + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n"
            + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36\r\n"
            + "Accept-Encoding: gzip,deflate,sdch\r\n"
            + "Accept-Language: en-US,en;q=0.8\r\n"
            + "Cookie: textwrapon=false; wysiwyg=textarea\r\n";
    String requestBody =
        "";
    return requestHeader + NEW_LINE + requestBody;
  }
}
