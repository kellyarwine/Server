package http.server.logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;

import static junit.framework.Assert.assertEquals;

@RunWith(JUnit4.class)
public class FileLoggerTest {
  private File logFile;
  private FileLogger logger;

  @Before
  public void setUp() throws IOException {
    File workingDirectory = new File(System.getProperty("user.dir"));
    logger = new FileLogger(workingDirectory);
    logFile = new File(workingDirectory, "server.log");
  }

  @After
  public void tearDown() {
    logFile.delete();
  }

  @Test
  public void printMessageToFile() throws IOException {
    logger.out("Hi");
    assertEquals("Hi\n", readServerLog());
  }

  @Test
  public void printMultipleMessagesToFile() throws IOException {
    logger.out("Hi");
    logger.out("Hi");
    assertEquals("Hi\nHi\n", readServerLog());
  }

  @Test
  public void printMultiLineRequestToFile() throws IOException {
    String requestHeader =  "POST /color_picker_post.html HTTP/1.1\r\n"
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
    String request        = requestHeader + "\r\n" + requestBody;
    logger.out(request);
    assertEquals(request+"\n", readServerLog());
  }

  private String readServerLog() throws IOException {
    InputStream inputStream = new FileInputStream(logFile);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    int chr;

    while ((chr = inputStream.read()) != -1)
      outputStream.write(chr);

    return outputStream.toString();
  }
}
