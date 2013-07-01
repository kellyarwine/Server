package http;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;

@RunWith(JUnit4.class)
  public class RequestHandlerTest {

  private WebServerSocket theServerSocket;
  private RequestHandler requestHandler;

  @Before
  public void setUp() throws IOException {
    theServerSocket = new MockServerSocket("This is some text.");
    requestHandler = new RequestHandler(theServerSocket);
    theServerSocket.connect();
  }

  @After
  public void tearDown() throws IOException {
    theServerSocket.closeConnection();
  }

  @Test
  public void testReadRequest() throws IOException {
    assertEquals("This is some text.", requestHandler.receiveRequest());
  }

  @Test
  public void testSendResponse() throws IOException {
    String expectedResponse = "This is some text.";
    byte[] expectedResponseInBytes = expectedResponse.getBytes();
    requestHandler.sendResponse(expectedResponseInBytes);
    String actualResponse = theServerSocket.out().toString();
    assertEquals(expectedResponse, actualResponse);
  }

}
