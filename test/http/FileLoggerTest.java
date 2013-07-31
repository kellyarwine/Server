package http;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;

import static junit.framework.Assert.assertEquals;

@RunWith(JUnit4.class)
public class FileLoggerTest {
  private String workingDirectory;
  private File logFile;
  private FileLogger io;

  @Before
  public void setUp() {
    workingDirectory = System.getProperty("user.dir");
    logFile = new File(workingDirectory + "/server.log");
    io = new FileLogger();
  }

  @After
  public void tearDown() {
    logFile.delete();
  }

  @Test
  public void printMessageToFileTest() throws IOException {
    io.out("Hi");
    assertEquals(io.currentDateAndTime() + " - Hi\r\n", readServerLog());
  }

  @Test
  public void printMultipleMessagesToFileTest() throws IOException {
    io.out("Hi");
    io.out("Bye");
    assertEquals(io.currentDateAndTime() + " - Hi\r\n" + io.currentDateAndTime() + " - Bye\r\n", readServerLog());
  }

  @Test
  public void printMultiLineRequestToFileTest() throws IOException {
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
    io.out(request);
    assertEquals(io.currentDateAndTime() + " - " + request + "\r\n", readServerLog());
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
