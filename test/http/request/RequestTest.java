package http.request;

import http.server.serverSocket.HttpServerSocket;
import http.server.serverSocket.MockHttpServerSocket;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class RequestTest {
  private String NEW_LINE = "\r\n";
  private File mockRequestsFile;
  private HttpServerSocket httpServerSocket;
  private Request request;

  @Before
  public void setUp() {
    File workingDirectoryFullPath = new File(System.getProperty("user.dir"));
    mockRequestsFile = new File(workingDirectoryFullPath, "test/mock_requests.tsv");
    request = new Request(new QueryStringRepository());
  }

  @After
  public void tearDown() {
    mockRequestsFile.delete();
  }

  @Test
  public void requestParts() throws IOException {
    String requestString  = "GET /color_picker_result.html HTTP/1.1\r\n"
        + "Host: localhost:5000\r\n"
        + "Connection: keep-alive\r\n"
        + "Content-Length: 15\r\n"
        + "Cache-Control: max-age=0\r\n"
        + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n"
        + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36\r\n"
        + "Accept-Encoding: gzip,deflate,sdch\r\n"
        + "Accept-Language: en-US,en;q=0.8\r\n"
        + "Cookie: textwrapon=false; wysiwyg=textarea\r\n"
        + NEW_LINE;
    setWebSocketWithMockRequests(requestString + "\t");
    HashMap actualResult = request.get(httpServerSocket.accept());
    assertEquals("GET", actualResult.get("httpMethod"));
    assertEquals("/color_picker_result.html", actualResult.get("url"));
    assertEquals("HTTP/1.1", actualResult.get("httpProtocol"));
    assertEquals("localhost:5000", actualResult.get("Host"));
    assertEquals("keep-alive", actualResult.get("Connection"));
    assertEquals("15", actualResult.get("Content-Length"));
    assertEquals("max-age=0", actualResult.get("Cache-Control"));
    assertEquals("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8", actualResult.get("Accept"));
    assertEquals("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36", actualResult.get("User-Agent"));
    assertEquals("gzip,deflate,sdch", actualResult.get("Accept-Encoding"));
    assertEquals("en-US,en;q=0.8", actualResult.get("Accept-Language"));
    assertEquals("textwrapon=false; wysiwyg=textarea", actualResult.get("Cookie"));
    assertEquals(null, actualResult.get("queryString"));
  }

  @Test
  public void multipleRequests() throws IOException {
    String requestString1  = "GET /color_picker_result.html HTTP/1.1\r\n"
        + "Host: localhost:5000\r\n"
        + NEW_LINE;
    String requestString2  = "GET /color_picker_post.html HTTP/1.1\r\n"
        + "Host: localhost:5000\r\n"
        + NEW_LINE;
    String requests = "";
    requests += requestString1;
    requests += "\t";
    requests += requestString2;
    setWebSocketWithMockRequests(requests);
    HashMap actualResult = request.get(httpServerSocket.accept());
    assertEquals("GET", actualResult.get("httpMethod"));
    assertEquals("/color_picker_result.html", actualResult.get("url"));
    assertEquals("HTTP/1.1", actualResult.get("httpProtocol"));
    assertEquals("localhost:5000", actualResult.get("Host"));
            actualResult = request.get(httpServerSocket.accept());
    assertEquals("GET", actualResult.get("httpMethod"));
    assertEquals("/color_picker_post.html", actualResult.get("url"));
    assertEquals("HTTP/1.1", actualResult.get("httpProtocol"));
    assertEquals("localhost:5000", actualResult.get("Host"));
  }

  @Test
  public void setGetQueryStringToSavedPostData() throws IOException {
    String requestHeader  = "POST /color_picker_post.html HTTP/1.1\r\n"
                          + "Host: localhost:5000\r\n"
                          + "Content-Length: 51\r\n";
    String requestBody    = "text_color1=blue&text_color2=red&text_color3=yellow";
    String requestString  = requestHeader + NEW_LINE + requestBody;
    String requests = requestString + "\t";
    setWebSocketWithMockRequests(requests);
    HashMap actualResult  = request.get(httpServerSocket.accept());

    assertEquals(requestBody, actualResult.get("queryString"));

           requestString  = "GET /color_picker_post.html HTTP/1.1\r\n"
                          + "Host: localhost:5000\r\n"
                          + NEW_LINE;
           requests = requestString + "\t";
    setWebSocketWithMockRequests(requests);
    actualResult  = request.get(httpServerSocket.accept());

    assertEquals(requestBody, actualResult.get("queryString"));
  }

  @Test
  public void setGetQueryStringToSavedPutData() throws IOException {
    String requestHeader  = "PUT /color_picker_post.html HTTP/1.1\r\n"
                          + "Host: localhost:5000\r\n"
                          + "Content-Length: 51\r\n";
    String requestBody    = "text_color1=blue&text_color2=red&text_color3=yellow";
    String requestString  = requestHeader + NEW_LINE + requestBody;
    String requests = requestString + "\t";
    setWebSocketWithMockRequests(requests);
    HashMap actualResult  = request.get(httpServerSocket.accept());

    assertEquals(requestBody, actualResult.get("queryString"));

           requestString  = "GET /color_picker_post.html HTTP/1.1\r\n"
                          + "Host: localhost:5000\r\n"
                          + NEW_LINE;
           requests = requestString + "\t";
    setWebSocketWithMockRequests(requests);
            actualResult  = request.get(httpServerSocket.accept());

    assertEquals(requestBody, actualResult.get("queryString"));
  }

  @Test
  public void setPostQueryStringToItsQueryString() throws IOException {
    String requestHeader  = "POST /color_picker_post.html HTTP/1.1\r\n"
                          + "Host: localhost:5000\r\n"
                          + "Content-Length: 51\r\n";
    String requestBody    = "text_color1=blue&text_color2=red&text_color3=yellow";
    String requestString  = requestHeader + NEW_LINE + requestBody;
    String requests = requestString + "\t";
    setWebSocketWithMockRequests(requests);
    HashMap actualResult  = request.get(httpServerSocket.accept());

    assertEquals(requestBody, actualResult.get("queryString"));

           requestHeader  = "POST /color_picker_post.html HTTP/1.1\r\n"
                          + "Host: localhost:5000\r\n"
                          + "Content-Length: 19\r\n";
           requestBody    = "text_color4=magenta";
           requestString  = requestHeader + NEW_LINE + requestBody;
           requests = requestString + "\t";
    mockRequestsFile.delete();
    setWebSocketWithMockRequests(requests);
            actualResult  = request.get(httpServerSocket.accept());

    assertEquals(requestBody, actualResult.get("queryString"));
  }

  @Test
  public void setPutQueryStringToItsQueryString() throws IOException {
    String requestHeader = "PUT /color_picker_post.html HTTP/1.1\r\n"
                         + "Host: localhost:5000\r\n"
                         + "Content-Length: 51\r\n";
    String requestBody   = "text_color1=blue&text_color2=red&text_color3=yellow";
    String requestString = requestHeader + NEW_LINE + requestBody;
    String requests = requestString + "\t";
    setWebSocketWithMockRequests(requests);
    HashMap actualResult = request.get(httpServerSocket.accept());

    assertEquals(requestBody, actualResult.get("queryString"));

           requestHeader = "POST /color_picker_post.html HTTP/1.1\r\n"
                         + "Host: localhost:5000\r\n"
                         + "Content-Length: 19\r\n";
           requestBody   = "text_color4=magenta";
           requestString = requestHeader + NEW_LINE + requestBody;
           requests = requestString + "\t";
    mockRequestsFile.delete();
    setWebSocketWithMockRequests(requests);
            actualResult = request.get(httpServerSocket.accept());

    assertEquals(requestBody, actualResult.get("queryString"));
  }

  @Test
  public void doNotSetGetQueryStringForNewRoute() throws IOException {
    String requestHeader  = "PUT /color_picker_post.html HTTP/1.1\r\n"
                          + "Host: localhost:5000\r\n"
                          + "Content-Length: 51\r\n";
    String requestBody    = "text_color1=blue&text_color2=red&text_color3=yellow";
    String requestString  = requestHeader + NEW_LINE + requestBody;
    String requests = requestString + "\t";
    setWebSocketWithMockRequests(requests);
    HashMap actualResult  = request.get(httpServerSocket.accept());

    assertEquals(requestBody, actualResult.get("queryString"));

           requestString  = "GET /color_picker.html HTTP/1.1\r\n"
                          + "Host: localhost:5000\r\n"
                          + NEW_LINE;
           requests = requestString + "\t";
    mockRequestsFile.delete();
    setWebSocketWithMockRequests(requests);
            actualResult  = request.get(httpServerSocket.accept());

    assertEquals(null, actualResult.get("queryString"));
  }

  @Test
  public void handlePutWithNoRequestBody() throws IOException {
    String requestHeader  = "PUT /color_picker_post.html HTTP/1.1\r\n"
        + "Host: localhost:5000\r\n";
    String requestString  = requestHeader + NEW_LINE;
    String requests = requestString + "\t";
    setWebSocketWithMockRequests(requests);
    HashMap actualResult  = request.get(httpServerSocket.accept());

    assertEquals(null, actualResult.get("queryString"));
  }

  public void setWebSocketWithMockRequests(String requestString) throws IOException {
    createMockRequestsTsv(requestString);
    httpServerSocket = new MockHttpServerSocket(mockRequestsFile.toString());
  }

  private void createMockRequestsTsv(String requestString) throws IOException {
    createMockRequestsFile();
    FileOutputStream fos = new FileOutputStream(mockRequestsFile, true);
    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos, "utf-8");
    Writer writer = new BufferedWriter(outputStreamWriter);
    writer.write(requestString + "\t");
    writer.close();
  }

  private void createMockRequestsFile() throws IOException {
    mockRequestsFile.createNewFile();
  }
}