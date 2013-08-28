//package http.server.logger;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import java.io.IOException;
//
//import static junit.framework.Assert.assertTrue;
//
//public class SystemLoggerFactoryTest {
//  SystemLoggerFactory systemLoggerFactory;
//
//  @Before
//  public void setUp() throws IOException {
//    systemLoggerFactory = new SystemLoggerFactory();
//  }
//
//  @Test
//  public void testSystemServerSocket() throws IOException {
//    assertTrue(systemLoggerFactory.get("production") instanceof ConsoleLogger);
//  }
//
//  @Test
//  public void testMockServerSocket() throws IOException {
//    assertTrue(systemLoggerFactory.get("test") instanceof ConsoleLogger);
//  }
//
//}
