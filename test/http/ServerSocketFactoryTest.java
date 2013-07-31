package http;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertTrue;

public class ServerSocketFactoryTest {
  ServerSocketFactory serverSocketFactory;

  @Before
  public void setUp() throws IOException {
    serverSocketFactory = new ServerSocketFactory();
  }

  @Test
  public void testSystemServerSocket() throws IOException {
    assertTrue(serverSocketFactory.get(6000, "production") instanceof SystemServerSocket);
  }

  @Test
  public void testMockServerSocket() throws IOException {
    assertTrue(serverSocketFactory.get(6000, "test") instanceof MockServerSocket);
  }

}
