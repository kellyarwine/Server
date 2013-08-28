package http.request;

import http.server.serverSocket.HttpServerSocket;
import http.server.serverSocket.MockHttpServerSocket;
import http.server.socket.WebSocket;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class RequestTest {
  private String NEW_LINE = "\r\n";
  private Request request;

  @Before
  public void setUp() {
    request = new Request(new QueryStringRepository());
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
        + "Cookie: textwrapon=false; wysiwyg=textarea\r\n";
    ArrayList requests = new ArrayList();
    requests.add(requestString);
    WebSocket webSocket = getWebSocket(requests);
    HashMap actualResult = request.get(webSocket);
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
        + "Host: localhost:5000\r\n";
    String requestString2  = "GET /color_picker_post.html HTTP/1.1\r\n"
        + "Host: localhost:5000\r\n";
    ArrayList requests = new ArrayList();
    requests.add(requestString1);
    requests.add(requestString2);
    WebSocket webSocket = getWebSocket(requests);
    HashMap actualResult = request.get(webSocket);
    assertEquals("GET", actualResult.get("httpMethod"));
    assertEquals("/color_picker_result.html", actualResult.get("url"));
    assertEquals("HTTP/1.1", actualResult.get("httpProtocol"));
    assertEquals("localhost:5000", actualResult.get("Host"));
              webSocket = getWebSocket(requests);
            actualResult = request.get(webSocket);
    assertEquals("GET", actualResult.get("httpMethod"));
    assertEquals("/color_picker_post.html", actualResult.get("url"));
    assertEquals("HTTP/1.1", actualResult.get("httpProtocol"));
    assertEquals("localhost:5000", actualResult.get("Host"));
  }

  @Test
  public void SetGetQueryStringToSavedPostData() throws IOException {
    String requestHeader  = "POST /color_picker_post.html HTTP/1.1\r\n"
                          + "Host: localhost:5000\r\n";
    String requestBody    = "text_color1=blue&text_color2=red&text_color3=yellow";
    String requestString  = requestHeader + NEW_LINE + requestBody;
    ArrayList requests = new ArrayList();
    requests.add(requestString);
    WebSocket webSocket = getWebSocket(requests);
    HashMap actualResult  = request.get(webSocket);

    assertEquals(requestBody, actualResult.get("queryString"));

           requestString  = "GET /color_picker_post.html HTTP/1.1\r\n"
                          + "Host: localhost:5000\r\n"
                          + NEW_LINE;
              requests = new ArrayList();
    requests.add(requestString);
              webSocket = getWebSocket(requests);
    actualResult  = request.get(webSocket);

    assertEquals(requestBody, actualResult.get("queryString"));
  }

  @Test
  public void SetGetQueryStringToSavedPutData() throws IOException {
    String requestHeader  = "PUT /color_picker_post.html HTTP/1.1\r\n"
                          + "Host: localhost:5000\r\n"
                          + NEW_LINE;
    String requestBody    = "text_color1=blue&text_color2=red&text_color3=yellow";
    String requestString  = requestHeader + NEW_LINE + requestBody;
    ArrayList requests = new ArrayList();
    requests.add(requestString);
    WebSocket webSocket   = getWebSocket(requests);
    HashMap actualResult  = request.get(webSocket);

    assertEquals(requestBody, actualResult.get("queryString"));

           requestString  = "GET /color_picker_post.html HTTP/1.1\r\n"
                          + "Host: localhost:5000\r\n"
                          + NEW_LINE;
              requests = new ArrayList();
    requests.add(requestString);
              webSocket = getWebSocket(requests);
            actualResult  = request.get(webSocket);

    assertEquals(requestBody, actualResult.get("queryString"));
  }

  @Test
  public void SetPostQueryStringToItsQueryString() throws IOException {
    String requestHeader  = "POST /color_picker_post.html HTTP/1.1\r\n"
                          + "Host: localhost:5000\r\n";
    String requestBody    = "text_color1=blue&text_color2=red&text_color3=yellow";
    String requestString  = requestHeader + NEW_LINE + requestBody;
    ArrayList requests = new ArrayList();
    requests.add(requestString);
    WebSocket webSocket = getWebSocket(requests);
    HashMap actualResult  = request.get(webSocket);

    assertEquals(requestBody, actualResult.get("queryString"));

           requestHeader  = "POST /color_picker_post.html HTTP/1.1\r\n"
                          + "Host: localhost:5000\r\n";
           requestBody    = "text_color4=magenta";
           requestString  = requestHeader + NEW_LINE + requestBody;
              requests = new ArrayList();
    requests.add(requestString);
              webSocket = getWebSocket(requests);
            actualResult  = request.get(webSocket);

    assertEquals(requestBody, actualResult.get("queryString"));
  }

  @Test
  public void SetPutQueryStringToItsQueryString() throws IOException {
    String requestHeader = "PUT /color_picker_post.html HTTP/1.1\r\n"
                         + "Host: localhost:5000\r\n";
    String requestBody   = "text_color1=blue&text_color2=red&text_color3=yellow";
    String requestString = requestHeader + NEW_LINE + requestBody;
    ArrayList requests = new ArrayList();
    requests.add(requestString);
    WebSocket webSocket = getWebSocket(requests);

    HashMap actualResult = request.get(webSocket);

    assertEquals(requestBody, actualResult.get("queryString"));

           requestHeader = "POST /color_picker_post.html HTTP/1.1\r\n"
                         + "Host: localhost:5000\r\n";
           requestBody   = "text_color4=magenta";
           requestString = requestHeader + NEW_LINE + requestBody;
              requests = new ArrayList();
    requests.add(requestString);
              webSocket = getWebSocket(requests);
            actualResult = request.get(webSocket);

    assertEquals(requestBody, actualResult.get("queryString"));
  }

  @Test
  public void DoNotSetGetQueryStringForNewRoute() throws IOException {
    String requestHeader  = "PUT /color_picker_post.html HTTP/1.1\r\n"
                          + "Host: localhost:5000\r\n";
    String requestBody    = "text_color1=blue&text_color2=red&text_color3=yellow";
    String requestString  = requestHeader + NEW_LINE + requestBody;
    ArrayList requests = new ArrayList();
    requests.add(requestString);
    WebSocket webSocket = getWebSocket(requests);
    HashMap actualResult  = request.get(webSocket);

    assertEquals(requestBody, actualResult.get("queryString"));

           requestString  = "GET /color_picker.html HTTP/1.1\r\n"
                          + "Host: localhost:5000\r\n"
                          + NEW_LINE;
              requests = new ArrayList();
    requests.add(requestString);
              webSocket = getWebSocket(requests);
            actualResult  = request.get(webSocket);

    assertEquals(null, actualResult.get("queryString"));
  }

  public WebSocket getWebSocket(ArrayList requests) throws IOException {
    HttpServerSocket httpServerSocket = new MockHttpServerSocket(requests);
    return httpServerSocket.accept();
  }
}