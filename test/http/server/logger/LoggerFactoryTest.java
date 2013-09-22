package http.server.logger;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.assertTrue;

public class LoggerFactoryTest {
  LoggerFactory loggerFactory;
  File workingDirectoryFullPath;

  @Before
  public void setUp() throws IOException {
    loggerFactory = new LoggerFactory();
    workingDirectoryFullPath = new File(System.getProperty("user.dir"));
  }

  @Test
  public void testSystemLogger() throws IOException {
    assertTrue(loggerFactory.build("production", workingDirectoryFullPath) instanceof ConsoleLogger);
  }

  @Test
  public void testMockLogger() throws IOException {
    assertTrue(loggerFactory.build("test", workingDirectoryFullPath) instanceof FileLogger);
  }

}
