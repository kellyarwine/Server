package http;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;

@RunWith(JUnit4.class)
public class BodyTest {

  private Body body;

  @Before
  public void setUp() throws IOException {
    body = new Body();
  }

  @Test
  public void testParseQueryString() throws IOException {
    assertArrayEquals(body.parseQueryString("text_color=red"), new String[] { "text_color", "red" });

  }

}