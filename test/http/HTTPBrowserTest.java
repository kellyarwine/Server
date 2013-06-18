package http;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
  public class HTTPBrowserTest {

  private WebSocketStreams streams;
  private HTTPBrowser browser;

  @Before
  public void setUp() throws IOException {
    streams = new MockSocketStreams("This is some text.");
    browser = new HTTPBrowser(streams, "/Users/Kelly/Desktop/Java_HTTP_Server/public");
  }

  @Test
  public void testReadRequest() throws IOException {
    assertEquals(browser.receiveRequest(), "This is some text.");
  }

  @Test
  public void testSendResponse() throws IOException {
    browser.sendResponse("This is some text.");
    assertEquals(browser.streams.out().toString(), "This is some text.");
  }

}
