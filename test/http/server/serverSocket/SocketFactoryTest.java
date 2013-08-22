//package http.server.serverSocket;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import java.io.IOException;
//
//import static junit.framework.Assert.assertTrue;
//
//public class SocketFactoryTest {
//  SocketFactory socketFactory;
//
//  @Before
//  public void setUp() throws IOException {
//    socketFactory = new SocketFactory();
//  }
//
//  @Test
//  public void testSystemServerSocket() throws IOException {
//    assertTrue(socketFactory.get(6000, "production") instanceof SystemSocket);
//  }
//
//  @Test
//  public void testMockServerSocket() throws IOException {
//    assertTrue(socketFactory.get(6000, "test") instanceof MockSocket);
//  }
//
//}
