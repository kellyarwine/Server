package http.server;

import org.junit.Before;

import java.io.File;

public class ServerTest {
  private String NEW_LINE = "\r\n";
  private File workingDirectory;
  private String publicDirectory;
  private File publicDirectoryFullPath;
  private Server server;

  @Before
  public void setUp() {
    workingDirectory = new File(System.getProperty("user.dir"));
    publicDirectory = "test/public/";
    publicDirectoryFullPath = new File(workingDirectory, publicDirectory);
  }

//  @Test
//  public void run() throws Exception {
//    ArrayList requests = new ArrayList();
//    requests.add(simpleRootRequest());
//    HttpServerSocket httpServerSocket = new MockHttpServerSocket(requests);
//    server = new Server("test", workingDirectory.toString(), publicDirectory, "routes.csv", ".htaccess", httpServerSocket, new FileLogger(workingDirectory));
//    assertTrue(server.serverThread.getDidRun());
//  }

  public String simpleRootRequest() {
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