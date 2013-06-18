package http;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
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

  @Test
  public void testWithNoQueryString() throws IOException {
    String expectedResult = "<HTML>\n"
                            + "  <HEAD>\n"
                            + "  </HEAD>\n"
                            + "  <BODY>\n"
                            + "    Hi, everyone!\n"
                            + "  </BODY>\n"
                            + "</HTML>";
    assertEquals(body.get("/Users/Kelly/Desktop/Java_HTTP_Server/public/hi_everyone.html", null), expectedResult);
  }

  @Test
  public void testWithQueryString() throws IOException {
    String expectedResult = "<HTML>\n"
                            + "  <HEAD>\n"
                            + "  </HEAD>\n"
                            + "  <BODY>\n"
                            + "    Hi, someone!\n"
                            + "  </BODY>\n"
                            + "</HTML>";
    assertEquals(body.get("/Users/Kelly/Desktop/Java_HTTP_Server/public/hi_everyone.html", "everyone=someone"), expectedResult);
  }

  @Test
  public void testContentLengthInBytes() throws IOException {
    String testString = "This is some text.";
    int expectedLength = 18; //Validated using byte calculator here: http://mothereff.in/byte-counter

    assert(body.contentLengthInBytes(testString) == expectedLength);
  }

}