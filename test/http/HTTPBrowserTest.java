package http;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;

@RunWith(JUnit4.class)
  public class HTTPBrowserTest {

  private WebSocketStreams streams;
  private HTTPBrowser browser;

  @Before
  public void setUp() throws IOException {
    streams = new MockSocketStreams("This is some text.");
    browser = new HTTPBrowser(streams, "./public");
  }

  @Test
  public void testReadRequest() throws IOException {
    streams = new MockSocketStreams("This is some text.");
    assertEquals("This is some text.", browser.receiveRequest());
  }

  @Test
  public void testReadRequestWithNewLine() throws IOException {
    streams = new MockSocketStreams("This is some text.\nThis is some more text.");
    assertEquals("This is some text.\nThis is some more text.", browser.receiveRequest());
  }

//  @Test
//  public void testSendResponse() throws IOException {
//    String responseString = "This is some text.";
//    byte[] responseInBytes = responseString.getBytes();
//    browser.sendResponse(responseInBytes);
//    assertEquals(responseString, String(browser.streams.out()));
//  }

}
