//package http.request;
//
//import http.server.socket.MockSocket;
//import http.server.socket.WebSocket;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.JUnit4;
//import java.io.IOException;
//import java.util.HashMap;
//import static org.junit.Assert.assertEquals;
//
//@RunWith(JUnit4.class)
//public class RequestTest {
//  private String NEWLINE = "\r\n";
//  private WebSocket theServerSocket;
//  private Request request;
//
//  @Test
//  public void requestParts() throws IOException {
//    String requestHeader  = "POST /color_picker_post.html HTTP/1.1\r\n"
//        + "Host: localhost:5000\r\n"
//        + "Connection: keep-alive\r\n"
//        + "Content-Length: 15\r\n"
//        + "Cache-Control: max-age=0\r\n"
//        + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n"
//        + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36\r\n"
//        + "Accept-Encoding: gzip,deflate,sdch\r\n"
//        + "Accept-Language: en-US,en;q=0.8\r\n"
//        + "Cookie: textwrapon=false; wysiwyg=textarea\r\n";
//    String requestBody    = "text_color=blue";
//    String requestString = requestHeader + NEWLINE + requestBody;
//    theServerSocket = new MockSocket(requestString);
//    theServerSocket.connect();
//    request = new Request();
//    request.get(theServerSocket);
//    HashMap actualResult = request.requestHash;
//
//    assertEquals("POST", actualResult.get("httpMethod"));
//    assertEquals("/color_picker_post.html", actualResult.get("url"));
//    assertEquals("HTTP/1.1", actualResult.get("httpProtocol"));
//    assertEquals("localhost:5000", actualResult.get("Host"));
//    assertEquals("keep-alive", actualResult.get("Connection"));
//    assertEquals("15", actualResult.get("Content-Length"));
//    assertEquals("max-age=0", actualResult.get("Cache-Control"));
//    assertEquals("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8", actualResult.get("Accept"));
//    assertEquals("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36", actualResult.get("User-Agent"));
//    assertEquals("gzip,deflate,sdch", actualResult.get("Accept-Encoding"));
//    assertEquals("en-US,en;q=0.8", actualResult.get("Accept-Language"));
//    assertEquals("textwrapon=false; wysiwyg=textarea", actualResult.get("Cookie"));
//    assertEquals(requestBody, actualResult.get("queryString"));
//  }
//
//  @Test
//  public void SetGetQueryStringToSavedPostData() throws IOException {
//    String requestHeader  = "POST /color_picker_post.html HTTP/1.1\r\n"
//                          + "Host: localhost:5000\r\n";
//    String requestBody    = "text_color1=blue&text_color2=red&text_color3=yellow";
//    String requestString = requestHeader + NEWLINE + requestBody;
//    theServerSocket = new MockSocket(requestString);
//    theServerSocket.connect();
//    request = new Request();
//    request.get(theServerSocket);
//    HashMap actualResult = request.requestHash;
//
//    assertEquals(requestBody, actualResult.get("queryString"));
//
//           requestString  = "GET /color_picker_post.html HTTP/1.1\r\n"
//                          + "Host: localhost:5000\r\n"
//                          + NEWLINE;
//    theServerSocket = new MockSocket(requestString);
//    theServerSocket.connect();
//    request.get(theServerSocket);
//            actualResult = request.requestHash;
//
//    assertEquals(requestBody, actualResult.get("queryString"));
//  }
//
//  @Test
//  public void SetGetQueryStringToSavedPutData() throws IOException {
//    String requestHeader  = "PUT /color_picker_post.html HTTP/1.1\r\n"
//        + "Host: localhost:5000\r\n"
//        + NEWLINE;
//    String requestBody    = "text_color1=blue&text_color2=red&text_color3=yellow";
//    String requestString  = requestHeader + NEWLINE + requestBody;
//    theServerSocket = new MockSocket(requestString);
//    theServerSocket.connect();
//    request = new Request();
//    request.get(theServerSocket);
//    HashMap actualResult = request.requestHash;
//
//    assertEquals(requestBody, actualResult.get("queryString"));
//
//           requestString  = "GET /color_picker_post.html HTTP/1.1\r\n"
//                          + "Host: localhost:5000\r\n"
//                          + NEWLINE;
//    theServerSocket = new MockSocket(requestString);
//    theServerSocket.connect();
//    request.get(theServerSocket);
//            actualResult = request.requestHash;
//
//    assertEquals(requestBody, actualResult.get("queryString"));
//  }
//
//  @Test
//  public void SetPostQueryStringToItsQueryString() throws IOException {
//    String requestHeader  = "POST /color_picker_post.html HTTP/1.1\r\n"
//                          + "Host: localhost:5000\r\n";
//    String requestBody    = "text_color1=blue&text_color2=red&text_color3=yellow";
//    String requestString = requestHeader + NEWLINE + requestBody;
//    theServerSocket = new MockSocket(requestString);
//    theServerSocket.connect();
//    request = new Request();
//    request.get(theServerSocket);
//    HashMap actualResult = request.requestHash;
//
//    assertEquals(requestBody, actualResult.get("queryString"));
//
//           requestHeader  = "POST /color_picker_post.html HTTP/1.1\r\n"
//                          + "Host: localhost:5000\r\n";
//    requestBody           = "text_color4=magenta";
//    requestString = requestHeader + NEWLINE + requestBody;
//    theServerSocket = new MockSocket(requestString);
//    theServerSocket.connect();
//    request.get(theServerSocket);
//            actualResult = request.requestHash;
//
//    assertEquals(requestBody, actualResult.get("queryString"));
//  }
//
//  @Test
//  public void SetPutQueryStringToItsQueryString() throws IOException {
//    String requestHeader = "PUT /color_picker_post.html HTTP/1.1\r\n"
//                         + "Host: localhost:5000\r\n";
//    String requestBody   = "text_color1=blue&text_color2=red&text_color3=yellow";
//    String requestString = requestHeader + NEWLINE + requestBody;
//    theServerSocket = new MockSocket(requestString);
//    theServerSocket.connect();
//    request = new Request();
//    request.get(theServerSocket);
//    HashMap actualResult = request.requestHash;
//
//    assertEquals(requestBody, actualResult.get("queryString"));
//
//           requestHeader = "POST /color_picker_post.html HTTP/1.1\r\n"
//                         + "Host: localhost:5000\r\n";
//           requestBody   = "text_color4=magenta";
//    requestString = requestHeader + NEWLINE + requestBody;
//    theServerSocket = new MockSocket(requestString);
//    theServerSocket.connect();
//    request.get(theServerSocket);
//            actualResult = request.requestHash;
//
//    assertEquals(requestBody, actualResult.get("queryString"));
//  }
//
//  @Test
//  public void DoNotSetGetQueryStringForNewRoute() throws IOException {
//    String requestHeader  = "PUT /color_picker_post.html HTTP/1.1\r\n"
//                          + "Host: localhost:5000\r\n";
//    String requestBody    = "text_color1=blue&text_color2=red&text_color3=yellow";
//    String requestString = requestHeader + NEWLINE + requestBody;
//    theServerSocket = new MockSocket(requestString);
//    theServerSocket.connect();
//    request = new Request();
//    request.get(theServerSocket);
//    HashMap actualResult = request.requestHash;
//
//    assertEquals(requestBody, actualResult.get("queryString"));
//
//           requestString  = "GET /color_picker.html HTTP/1.1\r\n"
//                          + "Host: localhost:5000\r\n"
//                          + NEWLINE;
//    theServerSocket = new MockSocket(requestString);
//    theServerSocket.connect();
//    request.get(theServerSocket);
//            actualResult = request.requestHash;
//
//    assertEquals(null, actualResult.get("queryString"));
//  }
//}