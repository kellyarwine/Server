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
    assertArrayEquals(new String[] { "text_color", "red" }, body.parseQueryString("text_color=red"));
  }

  @Test
  public void testBodyWithNoQueryString() throws IOException {
    String expectedResult = "<HTML>\n"
                            + "  <HEAD>\n"
                            + "  </HEAD>\n"
                            + "  <BODY>\n"
                            + "    Hi, everyone!\n"
                            + "  </BODY>\n"
                            + "</HTML>";
    byte[] actualResultInBytes = body.get("./public/hi_everyone.html", null);
    String actualResultString = new String(actualResultInBytes);
    assertEquals(expectedResult, actualResultString);
  }

  @Test
  public void testBodyWithQueryString() throws IOException {
    String expectedResult = "<HTML>\n"
                            + "  <HEAD>\n"
                            + "  </HEAD>\n"
                            + "  <BODY>\n"
                            + "    Hi, someone!\n"
                            + "  </BODY>\n"
                            + "</HTML>";

    byte[] actualResultInBytes = body.get("./public/hi_everyone.html", "everyone=someone");
    String actualResultString = new String(actualResultInBytes);
    assertEquals(expectedResult, actualResultString);
  }

  @Test
  public void testReplaceAll() {
    String key = "A";
    String value = "B";
    assertEquals("BBBB", "AAAA".replaceAll(key, value));
  }

}