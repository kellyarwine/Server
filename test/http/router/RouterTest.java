package http.router;

import http.request.Request;
import http.response.routeType.Directory;
import http.response.routeType.FileNotFound;
import http.response.routeType.Public;
import http.response.routeType.Redirect;
import http.server.serverSocket.WebServerSocket;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class RouterTest {

  private String NEW_LINE = "\r\n";
  private File publicDirectoryFullPath;
  private Request request;
  private WebServerSocket theServerSocket;
  private Router router;

  @Before
  public void setUp() throws IOException, URISyntaxException {
    String workingDirectory = System.getProperty("user.dir");
    publicDirectoryFullPath = new File(workingDirectory, "test/public/");
    router = new Router("test/public/", "routes.csv", ".htaccess");
  }

  @Test
  public void publicRoute() throws IOException {
    HashMap request = new HashMap();
    request.put("httpMethod", "GET");
    request.put("url", "/the_goal.html");
    request.put("httpProtocol", "HTTP/1.1");
    request.put("Host", "localhost:5000");
    request.put("Connection", "keep-alive");
    request.put("Content-Length", "15");
    request.put("Cache-Control", "max-age=0");
    request.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
    request.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36");
    request.put("Accept-Encoding", "gzip,deflate,sdch");
    request.put("Accept-Language", "en-US,en;q=0.8");
    request.put("Cookie", "textwrapon=false; wysiwyg=textarea");
    request.put("queryString", "text_color=blue");

    ArrayList actualResult = router.getRouteInfo(request);

    ArrayList expectedResult = new ArrayList();
    expectedResult.add(new File(publicDirectoryFullPath, "/the_goal.html"));
    expectedResult.add(new Public());

    assertEquals(expectedResult.get(0), actualResult.get(0));
    assertThat(actualResult.get(1), instanceOf(Public.class));
  }

  @Test
  public void redirectRoute() throws IOException {
    HashMap request = new HashMap();
    request.put("httpMethod", "GET");
    request.put("url", "/another_redirect");
    request.put("httpProtocol", "HTTP/1.1");
    request.put("Host", "localhost:5000");
    request.put("Connection", "keep-alive");
    request.put("Content-Length", "15");
    request.put("Cache-Control", "max-age=0");
    request.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
    request.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36");
    request.put("Accept-Encoding", "gzip,deflate,sdch");
    request.put("Accept-Language", "en-US,en;q=0.8");
    request.put("Cookie", "textwrapon=false; wysiwyg=textarea");
    request.put("queryString", "text_color=blue");

    ArrayList actualResult = router.getRouteInfo(request);

    ArrayList expectedResult = new ArrayList();
    expectedResult.add(new File(publicDirectoryFullPath, "/hi_everyone.html"));
    expectedResult.add(new Redirect());

    assertEquals(expectedResult.get(0), actualResult.get(0));
    assertThat(actualResult.get(1), instanceOf(Redirect.class));
  }

  @Test
  public void fileNotFoundRoute() throws IOException {
    HashMap request = new HashMap();
    request.put("httpMethod", "GET");
    request.put("url", "/this_route_does_not_exist");
    request.put("httpProtocol", "HTTP/1.1");
    request.put("Host", "localhost:5000");
    request.put("Connection", "keep-alive");
    request.put("Content-Length", "15");
    request.put("Cache-Control", "max-age=0");
    request.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
    request.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36");
    request.put("Accept-Encoding", "gzip,deflate,sdch");
    request.put("Accept-Language", "en-US,en;q=0.8");
    request.put("Cookie", "textwrapon=false; wysiwyg=textarea");
    request.put("queryString", "text_color=blue");

    ArrayList actualResult = router.getRouteInfo(request);

    ArrayList expectedResult = new ArrayList();
    expectedResult.add(new File(publicDirectoryFullPath, "/templates/404.html"));
    expectedResult.add(new FileNotFound());

    assertEquals(expectedResult.get(0), actualResult.get(0));
    assertThat(actualResult.get(1), instanceOf(FileNotFound.class));
  }

  @Test
  public void directoryRoute() throws IOException {
    HashMap request = new HashMap();
    request.put("httpMethod", "GET");
    request.put("url", "/images");
    request.put("httpProtocol", "HTTP/1.1");
    request.put("Host", "localhost:5000");
    request.put("Connection", "keep-alive");
    request.put("Content-Length", "15");
    request.put("Cache-Control", "max-age=0");
    request.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
    request.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36");
    request.put("Accept-Encoding", "gzip,deflate,sdch");
    request.put("Accept-Language", "en-US,en;q=0.8");
    request.put("Cookie", "textwrapon=false; wysiwyg=textarea");
    request.put("queryString", "text_color=blue");

    ArrayList actualResult = router.getRouteInfo(request);

    ArrayList expectedResult = new ArrayList();
    expectedResult.add(new File(publicDirectoryFullPath, "/templates/file_directory.html"));
    expectedResult.add(new Directory(publicDirectoryFullPath));

    assertEquals(expectedResult.get(0), actualResult.get(0));
    assertThat(actualResult.get(1), instanceOf(Directory.class));
  }
}