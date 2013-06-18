//package http;
//
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.JUnit4;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.ServerSocket;
//
//@RunWith(JUnit4.class)
//  public class HTTPBrowserTest {
//
//    private ServerSocket theServerSocket;
//    private HTTPBrowser browser;
//
//    @Before
//    public void setUp() throws IOException {
//      theServerSocket = new ServerSocket(5001);
//      browser = new HTTPBrowser(theServerSocket, "/Users/Kelly/Desktop/Java_HTTP_Server/public");
//    }
//
//    @After
//    public void tearDown() throws IOException {
//      theServerSocket.close();
//    }
//
//    @Test
//    public void testParseRequest() {
//      browser.parseRequest("GET /index.html HTTP/1.0");
//
//      assert(browser.HTTPRequest.equals("GET"));
//      assert(browser.route.equals("/Users/Kelly/Desktop/Java_HTTP_Server/public/index.html"));
//      assert(browser.HTTPVersion.equals("HTTP/1.0"));
//    }
//
//    @Test
//    public void testFileDoesExist() {
//      File theFile = new File("/Users/Kelly/Desktop/Java_HTTP_Server/public/404.html");
//
//      assert(theFile.canRead() == true);
//    }
//
//    @Test
//    public void testFileDoesNotExist() {
//      File theFile2 = new File("/Users/Kelly/Desktop/Java_HTTP_Server/public/donaldduck.html");
//
//      assert(theFile2.canRead() == false);
//    }
//
//    @Test
//    public void testParseRoute() {
//      String route = "/Users/Kelly/Desktop/Java_HTTP_Server/public/donaldduck.html?1";
//      String[] parseRoute = route.split("\\?");
//
//      assert(parseRoute[0].equals("/Users/Kelly/Desktop/Java_HTTP_Server/public/donaldduck.html"));
//      assert(parseRoute[1].equals("1"));
//    }
//
//    @Test
//    public void testParseRouteWithNoQuestionMark() {
//      String routeString = "/Users/Kelly/Desktop/Java_HTTP_Server/public/donaldduck.html";
//      String[] expectedResult = new String[] { "/Users/Kelly/Desktop/Java_HTTP_Server/public/donaldduck.html" };
//
//      Assert.assertArrayEquals(browser.parseRoute(routeString), expectedResult);
//    }
//
//    @Test
//    public void testGetResponseWithInvalidURL() {
//      browser.route = "/Users/Kelly/Desktop/Java_HTTP_Server/public/donaldduck.html?color=red";
//
////      assert(browser.out == "404 File Not Found");
//    }
//
//    @Test
//     public void testBodyWithQueryString() {
//      String queryString = "color=red";
//      String[] expectedResult = new String[]{"color", "red"};
//
//      Assert.assertArrayEquals(browser.parseQueryString(queryString), expectedResult);
//    }
//
//}