package http.server;

import org.junit.After;
import org.junit.Before;

import java.io.*;

public class ServerTest {
  private File publicDirectoryFullPath;
  private Server server;
  private String NEW_LINE = "\r\n";

  @Before
  public void setUp() throws Exception {
    String workingDirectory = System.getProperty("user.dir");
    publicDirectoryFullPath = new File(workingDirectory, "test/public/");
    server = new Server(5001, "test/public/", "production", "routes.csv", ".htaccess");
  }

  @After
  public void tearDown() {

  }

//  @Test
//  public void get() throws Exception {
//    String requestHeader  = "GET /hi_everyone.html HTTP/1.1\r\n"
//        + "Host: localhost:5000\r\n"
//        + "Connection: keep-alive\r\n"
//        + "Content-Length: 15\r\n"
//        + "Cache-Control: max-age=0\r\n"
//        + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n"
//        + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36\r\n"
//        + "Accept-Encoding: gzip,deflate,sdch\r\n"
//        + "Accept-Language: en-US,en;q=0.8\r\n"
//        + "Cookie: textwrapon=false; wysiwyg=textarea\r\n";
//    String requestString = requestHeader + NEW_LINE;
//    WebServerSocket theServerSocket = new MockServerSocket(requestString);
//    theServerSocket.connect();
//    server.run(theServerSocket, 5001, "test/public/", "production", "routes.csv", ".htaccess");
//    byte[] actualResult = theServerSocket.out().toString().getBytes();
//
//    String expectedHeaderString = "HTTP/1.1 200 OK\r\n"
//                                + new Date() + "\r\n"
//                                + "Server: NinjaServer 1.0" + "\r\n"
//                                + "Content-type: text/html; charset=UTF-8" + "\r\n"
//                                + "Content-length: 21552\r\n";
//    byte[] expectedHeader       = expectedHeaderString.getBytes();
//    byte[] expectedBody         = toBytes(new File(publicDirectoryFullPath, "/the_goal.html"));
//    byte[] expectedResult       = concatenate(new byte[][]{ expectedHeader, NEW_LINE.getBytes(), expectedBody });
//
//    assertEquals(new String(actualResult), new String(expectedResult));
//  }

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
}
