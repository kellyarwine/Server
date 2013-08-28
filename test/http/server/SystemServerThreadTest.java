//package http.server;
//
//import http.request.QueryStringRepository;
//import http.server.logger.FileLogger;
//import http.server.serverSocket.HttpServerSocket;
//import http.server.serverSocket.MockHttpServerSocket;
//import http.server.socket.WebSocket;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import static junit.framework.Assert.assertEquals;
//import static org.junit.Assert.assertArrayEquals;
//
//public class SystemServerThreadTest {
//  private String NEW_LINE = "\r\n";
//  private File workingDirectory;
//  private String publicDirectory;
//  private File publicDirectoryFullPath;
//
//  @Before
//  public void setUp() {
//    workingDirectory = new File(System.getProperty("user.dir"));
//    publicDirectory = "test/public/";
//    publicDirectoryFullPath = new File(workingDirectory, publicDirectory);
//  }
//
//  @After
//  public void tearDown() {
//    deleteDirectory(new File(publicDirectoryFullPath, "/templates"));
//  }
//
//  @Test
//  public void publicRoute() throws IOException {
//    Map serverConfig;
//    serverConfig = new HashMap();
//    serverConfig.put("port", "5000");
//    ArrayList requests = new ArrayList();
//    requests.add(publicRequest());
//    requests.add(directoryRequest());
//    WebSocket webSocket = getWebSocket(requests);
//    SystemServerThread systemServerThread = new SystemServerThread(serverConfig, new FileLogger(workingDirectory), webSocket, new QueryStringRepository());
//    systemServerThread.run();
//    String actualResult = webSocket.out().toString();
//
//    String expectedHeaderString = "HTTP/1.1 200 OK\r\n"
//        + new Date() + "\r\n"
//        + "Server: NinjaServer 1.0" + "\r\n"
//        + "Content-type: text/html; charset=UTF-8" + "\r\n"
//        + "Content-length: 21552\r\n";
//    byte[] expectedHeader       = expectedHeaderString.getBytes();
//    byte[] expectedBody         = toBytes(new File(publicDirectoryFullPath, "/the_goal.html"));
//    byte[] expectedResult       = concatenate(new byte[][]{ expectedHeader, NEW_LINE.getBytes(), expectedBody });
//
//    assertEquals(new String(expectedResult), actualResult);
//    assertArrayEquals(expectedResult, actualResult.getBytes());
//
//    systemServerThread.run();
//    String actualResult2 = webSocket.out().toString();
//
//    String expectedHeaderString2 = "HTTP/1.1 200 OK\r\n"
//        + new Date() + "\r\n"
//        + "Server: NinjaServer 1.0" + "\r\n"
//        + "Content-type: text/html; charset=UTF-8" + "\r\n"
//        + "Content-length: 21552\r\n";
//    byte[] expectedHeader2       = expectedHeaderString2.getBytes();
//    byte[] expectedBody2         = toBytes(new File(publicDirectoryFullPath, "/the_goal.html"));
//    byte[] expectedResult2       = concatenate(new byte[][]{ expectedHeader2, NEW_LINE.getBytes(), expectedBody2 });
//
//    assertEquals(new String(expectedResult2), actualResult2);
//    assertArrayEquals(expectedResult2, actualResult2.getBytes());
//  }
//
//  public WebSocket getWebSocket(ArrayList requests) throws IOException {
//    HttpServerSocket httpServerSocket = new MockHttpServerSocket(requests);
//    return httpServerSocket.accept();
//  }
//
//  public byte[] toBytes(File routeFile) throws IOException {
//    InputStream inputStream = new FileInputStream(routeFile);
//    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//    int chr;
//
//    while ((chr = inputStream.read()) != -1)
//      outputStream.write(chr);
//
//    return outputStream.toByteArray();
//  }
//
//  public byte[] concatenate(byte[][] byteArray) throws IOException {
//    ByteArrayOutputStream bOutput = new ByteArrayOutputStream();
//    for(int i=0; i<byteArray.length; i++) {
//      bOutput.write(byteArray[i]);
//    }
//    return bOutput.toByteArray();
//  }
//
//  private void deleteDirectory(File directory) {
//    if (directory.isDirectory()) {
//      String[] children = directory.list();
//      for (int i=0; i<children.length; i++) {
//        deleteDirectory(new File(directory, children[i]));
//      }
//    }
//    directory.delete();
//  }
//
//  public String simpleRootRequest() {
//    String requestHeader =
//        "GET / HTTP/1.1\r\n"
//            + "Host: localhost:5000\r\n"
//            + "Connection: keep-alive\r\n"
//            + "Content-Length: 15\r\n"
//            + "Cache-Control: max-age=0\r\n"
//            + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n"
//            + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36\r\n"
//            + "Accept-Encoding: gzip,deflate,sdch\r\n"
//            + "Accept-Language: en-US,en;q=0.8\r\n"
//            + "Cookie: textwrapon=false; wysiwyg=textarea\r\n";
//    String requestBody =
//        "";
//    return requestHeader + NEW_LINE + requestBody;
//  }
//
//  public String redirectRequest() {
//    String requestHeader =
//        "GET /redirect HTTP/1.1\r\n"
//            + "Host: localhost:5000\r\n"
//            + "Connection: keep-alive\r\n"
//            + "Content-Length: 15\r\n"
//            + "Cache-Control: max-age=0\r\n"
//            + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n"
//            + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36\r\n"
//            + "Accept-Encoding: gzip,deflate,sdch\r\n"
//            + "Accept-Language: en-US,en;q=0.8\r\n"
//            + "Cookie: textwrapon=false; wysiwyg=textarea\r\n";
//    String requestBody =
//        "";
//    return requestHeader + NEW_LINE + requestBody;
//  }
//
//  public String fileNotFoundRequest() {
//    String requestHeader =
//        "GET /this_url_does_not_exist HTTP/1.1\r\n"
//            + "Host: localhost:5000\r\n"
//            + "Connection: keep-alive\r\n"
//            + "Content-Length: 15\r\n"
//            + "Cache-Control: max-age=0\r\n"
//            + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n"
//            + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36\r\n"
//            + "Accept-Encoding: gzip,deflate,sdch\r\n"
//            + "Accept-Language: en-US,en;q=0.8\r\n"
//            + "Cookie: textwrapon=false; wysiwyg=textarea\r\n";
//    String requestBody =
//        "";
//    return requestHeader + NEW_LINE + requestBody;
//  }
//
//  public String publicRequest() {
//    String requestHeader =
//        "GET /the_goal.html HTTP/1.1\r\n"
//            + "Host: localhost:5000\r\n"
//            + "Connection: keep-alive\r\n"
//            + "Content-Length: 15\r\n"
//            + "Cache-Control: max-age=0\r\n"
//            + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n"
//            + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36\r\n"
//            + "Accept-Encoding: gzip,deflate,sdch\r\n"
//            + "Accept-Language: en-US,en;q=0.8\r\n"
//            + "Cookie: textwrapon=false; wysiwyg=textarea\r\n";
//    String requestBody =
//        "";
//    return requestHeader + NEW_LINE + requestBody;
//  }
//
//  public String directoryRequest() {
//    String requestHeader =
//        "GET /the_goal.html HTTP/1.1\r\n"
//            + "Host: localhost:5000\r\n"
//            + "Connection: keep-alive\r\n"
//            + "Content-Length: 15\r\n"
//            + "Cache-Control: max-age=0\r\n"
//            + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n"
//            + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36\r\n"
//            + "Accept-Encoding: gzip,deflate,sdch\r\n"
//            + "Accept-Language: en-US,en;q=0.8\r\n"
//            + "Cookie: textwrapon=false; wysiwyg=textarea\r\n";
//    String requestBody =
//        "";
//    return requestHeader + NEW_LINE + requestBody;
//  }
//}