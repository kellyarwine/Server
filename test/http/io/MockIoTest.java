package http.io;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class MockIoTest {
  private MockIo mockIo;
  private File logFile;

  @Before
  public void setUp() {
    ArrayList commands = new ArrayList();
    commands.add("Go forward.");
    mockIo = new MockIo(commands);
    logFile = new File(System.getProperty("user.dir"), "output.log");
  }

  @After
  public void tearDown() {
    logFile.delete();
  }

  @Test
  public void in() throws InterruptedException {
    assertEquals("Go forward.", mockIo.in());
  }

  @Test
  public void out() throws IOException {
    mockIo.out("Move back.");
    assertEquals("Move back.\n", readLog());
  }

  private String readLog() throws IOException {
    InputStream inputStream = new FileInputStream(logFile);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    int chr;

    while ((chr = inputStream.read()) != -1)
      outputStream.write(chr);

    return outputStream.toString();
  }
}
