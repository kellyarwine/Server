package http.server;

import http.server.serverSocket.MockServerSocket;
import http.server.serverSocket.WebServerSocket;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

public class ServerTest {
  private File publicDirectoryFullPath;
  private Server server;
  private String NEW_LINE = "\r\n";

  @Before
  public void setUp() throws Exception {
    ArrayList requests = new ArrayList();

    for (int i = 0; i < 10; i++)
      requests.add(simpleRootRequest());

    WebServerSocket serverSocket = new MockServerSocket(requests);
//    SystemLogger logger = new FileLogger();
    String publicDirectory = "test/public/";
    String routesFilePath = "routes.csv";
    String htAccessFilePath = ".htaccess";
   // server = new Server(serverSocket, logger, publicDirectory, routesFilePath, htAccessFilePath);
  }

  @After
  public void tearDown() {
    String workingDirectory = System.getProperty("user.dir");
    File logFile = new File(workingDirectory + "/server.log");
//    logFile.delete();
  }

  @Test
  public void get() throws Exception {
  }

  public String simpleRootRequest() {
        String requestHeader  = "GET / HTTP/1.1\r\n"
        + "Host: localhost:5000\r\n"
        + "Connection: keep-alive\r\n"
        + "Content-Length: 15\r\n"
        + "Cache-Control: max-age=0\r\n"
        + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n"
        + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36\r\n"
        + "Accept-Encoding: gzip,deflate,sdch\r\n"
        + "Accept-Language: en-US,en;q=0.8\r\n"
        + "Cookie: textwrapon=false; wysiwyg=textarea\r\n";
    String requestBody    = "text_color=blue";
    return requestHeader + NEW_LINE + requestBody;
  }
}
