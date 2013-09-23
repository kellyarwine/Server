package http.server.serverSocket;

import http.server.socket.WebSocket;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class MockHttpServerSocketTest {
  private String NEW_LINE = "\n";
  private File mockRequestsFile;

  @Before
  public void setUp() throws IOException {
    File workingDirectoryFullPath = new File(System.getProperty("user.dir"));
    mockRequestsFile = new File(workingDirectoryFullPath, "test/mock_requests.tsv");
  }

  @After
  public void tearDown() {
    mockRequestsFile.delete();
  }

  @Test
  public void close() throws IOException {
    createMockRequestsTsv("");
    HttpServerSocket httpServerSocket = new MockHttpServerSocket(mockRequestsFile.toString());
    httpServerSocket.close();
    assertTrue(httpServerSocket.isClosed());
  }

  @Test
  public void bound() throws IOException {
    createMockRequestsTsv("");
    HttpServerSocket httpServerSocket = new MockHttpServerSocket(mockRequestsFile.toString());
    assertFalse(httpServerSocket.isBound());
  }

  @Test
  public void accept() throws IOException {
    createMockRequestsTsv(simpleRootRequest());
    HttpServerSocket httpServerSocket = new MockHttpServerSocket(mockRequestsFile.toString());
    WebSocket webSocket = httpServerSocket.accept();
    assertThat(webSocket, instanceOf(WebSocket.class));
    String actualResult = read(webSocket);
    assertEquals(simpleRootRequest(), actualResult);
  }

  @Test
  public void acceptMultipleRequests() throws IOException {
    createMockRequestsTsv(simpleRootRequestTimesTwo());
    HttpServerSocket httpServerSocket = new MockHttpServerSocket(mockRequestsFile.toString());
    WebSocket webSocket = httpServerSocket.accept();
    assertThat(webSocket, instanceOf(WebSocket.class));
    String actualResult = read(webSocket);
    assertEquals(simpleRootRequest(), actualResult);
  }

  private String simpleRootRequest() {
    String requestHeader =
        "GET / HTTP/1.1\n"
            + "Host: localhost:5000\n"
            + "Connection: keep-alive\n"
            + "Content-Length: 15\n"
            + "Cache-Control: max-age=0\n"
            + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n"
            + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36\n"
            + "Accept-Encoding: gzip,deflate,sdch\n"
            + "Accept-Language: en-US,en;q=0.8\n"
            + "Cookie: textwrapon=false; wysiwyg=textarea\n";
    return requestHeader + NEW_LINE;
  }

  private String simpleRootRequestTimesTwo() {
    String requestHeader =
        "GET / HTTP/1.1\n"
            + "Host: localhost:5000\n"
            + "Connection: keep-alive\n"
            + "Content-Length: 15\n"
            + "Cache-Control: max-age=0\n"
            + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n"
            + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36\n"
            + "Accept-Encoding: gzip,deflate,sdch\n"
            + "Accept-Language: en-US,en;q=0.8\n"
            + "Cookie: textwrapon=false; wysiwyg=textarea\n";
    return requestHeader + NEW_LINE + "\t" + requestHeader + NEW_LINE;
  }

  private String read(WebSocket webSocket) throws IOException {
    int chr;
    StringBuffer buffer = new StringBuffer();

    while ( (chr = webSocket.in().read()) != -1 ) {
      buffer.append((char) chr);
      if ( !webSocket.in().ready())
        break;
    }
    return buffer.toString();
  }

  private void createMockRequestsTsv(String requestString) throws IOException {
    createMockRequestsFile();
    FileOutputStream fos = new FileOutputStream(mockRequestsFile, true);
    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos, "utf-8");
    Writer writer = new BufferedWriter(outputStreamWriter);
    writer.append(requestString + "\t");
    writer.close();
  }

  private void createMockRequestsFile() throws IOException {
    mockRequestsFile.createNewFile();
  }
}