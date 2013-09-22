package http.server.socket;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.io.IOException;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class MockSocketTest {
  private WebSocket webSocket;

  @Before
  public void setUp() throws IOException {
    webSocket = new MockSocket("This is some text.");
  }

  @Test
  public void testIn() throws IOException {
    assertEquals("This is some text.", webSocket.in().readLine());
  }

  @Test
  public void testOut() throws IOException {
    webSocket.out().write("This is some text.".getBytes());
    assertEquals("This is some text.", webSocket.out().toString());
  }

  @Test
  public void testClosed() throws IOException {
    webSocket.close();
    assertTrue(webSocket.getIsClosed());
  }
}