package http.server.logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static junit.framework.Assert.assertEquals;

public class SystemLoggerTest {
  private File workingDirectory;
  private File logFile;
  private File consoleFile;

  @Before
  public void setUp() throws IOException {
    workingDirectory = new File(System.getProperty("user.dir"));
    logFile = new File(workingDirectory + "/server.log");
    consoleFile = new File(workingDirectory + "/console.txt");
  }

  @After
  public void tearDown() {
    logFile.delete();
    consoleFile.delete();
  }

  @Test
  public void printMultipleMessagesToFile() throws IOException {
    SystemLogger logger = new FileLogger(workingDirectory);
    logger.logMessage("This message should be printed to the server log.");
    logger.logMessage("This message should also be printed to the server log.");
    String expectedResult = "[" + logger.currentDateAndTime() + "] This message should be printed to the server log.\n"
        + "[" + logger.currentDateAndTime() + "] This message should also be printed to the server log.\n";

    assertEquals(expectedResult, readServerLog(logFile));
  }

  @Test
  public void printMultipleMessagesToConsole() throws IOException {
    SystemLogger logger = new ConsoleLogger();
    System.setOut(new PrintStream(consoleFile));
    logger.logMessage("This message should be printed to the console.");
    logger.logMessage("This message should also be printed to the console.");

    String expectedResult = "[" + logger.currentDateAndTime() + "] This message should be printed to the console.\n"
                          + "[" + logger.currentDateAndTime() + "] This message should also be printed to the console.\n";

    assertEquals(expectedResult, readServerLog(consoleFile));
  }

  private String readServerLog(File file) throws IOException {
    InputStream inputStream = new FileInputStream(file);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    int chr;

    while ((chr = inputStream.read()) != -1)
      outputStream.write(chr);

    return outputStream.toString();
  }
}