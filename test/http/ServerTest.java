package http;

import org.junit.After;
import org.junit.Before;

public class ServerTest {
  private Server server;

  @Before
  public void setUp() throws Exception {
    server = new Server(5000, "public/", "production");
  }

  @After
  public void tearDown() {

  }

//  @Test
//  public void RemoveTrailingForwardSlashTest() throws Exception {
//
//    String publicDirectory = server.removeTrailingForwardSlashFromPath("/public");
//    assertEquals("public", publicDirectory);
//  }
}
