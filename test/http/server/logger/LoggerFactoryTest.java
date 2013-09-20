package http.server.logger;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertTrue;

public class LoggerFactoryTest {
  LoggerFactory loggerFactory;

  @Before
  public void setUp() throws IOException {
    loggerFactory = new LoggerFactory();
  }

  @Test
  public void testSystemServerSocket() throws IOException {
    assertTrue(loggerFactory.build("production") instanceof ConsoleLogger);
  }

  @Test
  public void testMockServerSocket() throws IOException {
    assertTrue(loggerFactory.build("test") instanceof ConsoleLogger);
  }

}
