package http.response;

import http.request.QueryStringRepository;
import http.request.Request;
import http.response.routeType.Directory;
import http.response.routeType.FileNotFound;
import http.response.routeType.Public;
import http.response.routeType.Redirect;
import http.router.Templater;
import http.server.serverSocket.HttpServerSocket;
import http.server.serverSocket.MockHttpServerSocket;
import http.server.socket.WebSocket;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

@RunWith(JUnit4.class)
public class ResponseTest {

  private String NEW_LINE = "\r\n";
  private File workingDirectory;
  private File publicDirectoryFullPath;
  private WebSocket theServerSocket;
  private Response response;

  @Before
  public void setUp() throws IOException {
    workingDirectory = new File(System.getProperty("user.dir"));
    publicDirectoryFullPath = new File(workingDirectory, "test/public/");
    response = new Response();
    Templater templater = new Templater(publicDirectoryFullPath);
    templater.createTemplate("404.html");
    templater.createTemplate("file_directory.html");
  }

  @After
  public void tearDown() {
    deleteDirectory(new File(publicDirectoryFullPath, "/templates"));
  }

  @Test
  public void publicRoute() throws IOException {
    String requestHeader  = "GET /the_goal.html HTTP/1.1\r\n"
                          + "Host: localhost:5000\r\n";
    String requestString  = requestHeader + NEW_LINE;
    WebSocket webSocket   = getWebSocket(requestString);
    Request request       = new Request(new QueryStringRepository());
    HashMap requestMap    = request.get(webSocket);

    ArrayList routeInfo = new ArrayList();
    routeInfo.add(new File(publicDirectoryFullPath, (String) requestMap.get("url")));
    routeInfo.add(new Public());
    response.send(webSocket.out(), requestMap, routeInfo);
    byte[] actualResult = webSocket.out().toString().getBytes();

    String expectedHeaderString = "HTTP/1.1 200 OK\r\n"
                                + new Date() + "\r\n"
                                + "Server: NinjaServer 1.0" + "\r\n"
                                + "Content-type: text/html; charset=UTF-8" + "\r\n"
                                + "Content-length: 21552\r\n";
    byte[] expectedHeader       = expectedHeaderString.getBytes();
    byte[] expectedBody         = toBytes(new File(publicDirectoryFullPath, "/the_goal.html"));
    byte[] expectedResult       = concatenate(new byte[][]{ expectedHeader, NEW_LINE.getBytes(), expectedBody });
    assertEquals(new String(expectedResult), new String(actualResult));
    assertArrayEquals(expectedResult, actualResult);
  }


  @Test
  public void redirectRoute() throws IOException {
    String requestHeader  = "GET /redirect HTTP/1.1\r\n"
        + "Host: localhost:5000\r\n";
    String requestString  = requestHeader + NEW_LINE;
    WebSocket webSocket   = getWebSocket(requestString);
    Request request       = new Request(new QueryStringRepository());
    HashMap requestMap    = request.get(webSocket);

    ArrayList routeInfo = new ArrayList();
    routeInfo.add(new File(publicDirectoryFullPath, "hi_everyone.html"));
    routeInfo.add(new Redirect());
    response.send(webSocket.out(), requestMap, routeInfo);
    byte[] actualResult = webSocket.out().toString().getBytes();

    String expectedHeaderString = "HTTP/1.1 301 Moved Permanently\r\n"
                                + new Date() + "\r\n"
                                + "Server: NinjaServer 1.0" + "\r\n"
                                + "Content-type: text/html; charset=UTF-8" + "\r\n"
                                + "Content-length: 0\r\n"
                                + "Location: http://localhost:5000/hi_everyone.html\r\n";
    byte[] expectedHeader       = expectedHeaderString.getBytes();
    byte[] expectedBody         = "".getBytes();
    byte[] expectedResult       = concatenate(new byte[][]{ expectedHeader, NEW_LINE.getBytes(), expectedBody });
    assertEquals(new String(expectedResult), new String(actualResult));
    assertArrayEquals(expectedResult, actualResult);
  }

  @Test
  public void fileNotFoundRoute() throws IOException {
    String requestHeader  = "GET /this_url_does_not_exist HTTP/1.1\r\n"
                          + "Host: localhost:5000\r\n";
    String requestBody    = "text_color=blue";
    String requestString  = requestHeader + NEW_LINE + requestBody;
    WebSocket webSocket   = getWebSocket(requestString);
    Request request       = new Request(new QueryStringRepository());
    HashMap requestMap    = request.get(webSocket);

    ArrayList routeInfo = new ArrayList();
    routeInfo.add(new File(publicDirectoryFullPath, "/templates/404.html"));
    routeInfo.add(new FileNotFound());
    response.send(webSocket.out(), requestMap, routeInfo);
    byte[] actualResult = webSocket.out().toString().getBytes();

    String expectedHeaderString = "HTTP/1.1 404 File Not Found\r\n"
        + new Date() + "\r\n"
        + "Server: NinjaServer 1.0" + "\r\n"
        + "Content-type: text/html; charset=UTF-8" + "\r\n"
        + "Content-length: 127\r\n";
    byte[] expectedHeader       = expectedHeaderString.getBytes();
    byte[] expectedBody         = toBytes(new File(workingDirectory, "src/http/templates/404.html"));
    byte[] expectedResult       = concatenate(new byte[][]{ expectedHeader, NEW_LINE.getBytes(), expectedBody });
    assertEquals(new String(expectedResult), new String(actualResult));
    assertArrayEquals(expectedResult, actualResult);
  }

  @Test
  public void directoryRoute() throws IOException {
    String requestHeader  = "GET /images HTTP/1.1\r\n"
                          + "Host: localhost:5000\r\n";
    String requestString  = requestHeader + NEW_LINE;
    WebSocket webSocket   = getWebSocket(requestString);
    Request request       = new Request(new QueryStringRepository());
    HashMap requestMap    = request.get(webSocket);

    ArrayList routeInfo = new ArrayList();
    routeInfo.add(new File(publicDirectoryFullPath, "/templates/file_directory.html"));
    routeInfo.add(new Directory(publicDirectoryFullPath));
    response.send(webSocket.out(), requestMap, routeInfo);
    byte[] actualResult = webSocket.out().toString().getBytes();

    String expectedHeader      = "HTTP/1.1 200 OK\r\n"
                               + new Date() + "\r\n"
                               + "Server: NinjaServer 1.0" + "\r\n"
                               + "Content-type: text/html; charset=UTF-8" + "\r\n"
                               + "Content-length: 229\r\n";
    String expectedBody        = "<HTML>\n"
                               + "  <HEAD>\n"
                               + "    <TITLE>\n"
                               + "      File Directory\n"
                               + "    </TITLE>\n"
                               + "  </HEAD>\n"
                               + "  <BODY>\n"
                               + "    <H1>/images Folder</H1>\n"
                               + "    <a href=\"/images/404.png\">404.png</a><br><a href=\"/images/another_404.png\">another_404.png</a><br>\n"
                               + "  </BODY>\n"
                               + "</HTML>";
    String expectedResult      = expectedHeader + NEW_LINE + expectedBody;
    assertEquals(expectedResult, new String(actualResult));
    assertArrayEquals(expectedResult.getBytes(), actualResult);
  }

  public byte[] toBytes(File routeFile) throws IOException {
      InputStream inputStream = new FileInputStream(routeFile);
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      int chr;

      while ((chr = inputStream.read()) != -1)
        outputStream.write(chr);

      return outputStream.toByteArray();
  }

  public byte[] concatenate(byte[][] byteArray) throws IOException {
    ByteArrayOutputStream bOutput = new ByteArrayOutputStream();
    for(int i=0; i<byteArray.length; i++) {
      bOutput.write(byteArray[i]);
    }
    return bOutput.toByteArray();
  }

  private void deleteDirectory(File directory) {
    if (directory.isDirectory()) {
      String[] children = directory.list();
      for (int i=0; i<children.length; i++) {
        deleteDirectory(new File(directory, children[i]));
      }
    }
    directory.delete();
  }

  public WebSocket getWebSocket(String request) throws IOException {
    ArrayList requests = new ArrayList();
    requests.add(request);
    HttpServerSocket httpServerSocket = new MockHttpServerSocket(requests);
    return httpServerSocket.accept();
  }
}