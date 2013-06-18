package http;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;

@RunWith(JUnit4.class)
  public class HTTPBrowser2Test {

  private WebSocketStreams streams;
  private HTTPBrowser2 browser;

  @Before
  public void setUp() throws IOException {
    streams = new MockSocketStreams("This is some text.");
    browser = new HTTPBrowser2(streams, "/Users/Kelly/Desktop/Java_HTTP_Server/public");
  }

  @Test
  public void testReadRequest() throws IOException {
    assertEquals(browser.readRequest(), "This is some text.");
  }

}
