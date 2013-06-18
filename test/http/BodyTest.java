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

  @Test
  public void testContentLengthInBytes2() throws IOException {
    String testString =
    "<HTML>\n" +
    "  <HEAD>\n" +
    "    <TITLE>\n" +
    "      Sample Form\n" +
    "    </TITLE>\n" +
    "  </HEAD>\n" +
    "  <BODY>\n" +
    "    <form name=\"color_picker\" action=\"/color_picker_result.html\" method=\"GET\">\n" +
    "<br>\n" +
    "    <p>I want to change the color of the text on the next webpage.</p>\n" +
    "    <input type=\"radio\" name=\"text_color\" value=\"red\" checked>Red<br>\n" +
    "    <input type=\"radio\" name=\"text_color\" value=\"blue\">Blue<br>\n" +
    "    <input type=\"radio\" name=\"text_color\" value=\"green\">Green<br><br>\n" +
    "    <INPUT TYPE = \"SUBMIT\">\n" +
    "  </BODY>\n" +
    "</HTML>";

    int expectedLength = 482; //Validated using byte calculator here: http://mothereff.in/byte-counter

    assert(body.contentLengthInBytes(testString) == expectedLength);
  }
}