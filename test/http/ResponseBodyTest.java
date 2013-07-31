package http;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class ResponseBodyTest {

  private ResponseBody responseBody;
  private String workingDirectory;
  private String publicDirectory;
  private File publicDirectoryFullPath;

  @Before
  public void setUp() throws IOException {
    workingDirectory = System.getProperty("user.dir");
    publicDirectory = "public/";
    publicDirectoryFullPath = new File(workingDirectory, publicDirectory);
    responseBody = new ResponseBody(publicDirectoryFullPath);
  }

  @Test
  public void testParseQueryString() throws IOException {
    assertArrayEquals(new String[] { "text_color", "red" }, responseBody.parseKeyValuePairs("text_color=red"));
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
    File routeFile = new File(publicDirectoryFullPath + "/hi_everyone.html");
    byte[] actualResultInBytes = responseBody.get(routeFile, null);
    String actualResult = new String(actualResultInBytes);
    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void testBodyWithOneKeyValuePair() throws IOException {
    String expectedResult = "<HTML>\n"
                            + "  <HEAD>\n"
                            + "  </HEAD>\n"
                            + "  <BODY>\n"
                            + "    Hi, someone!\n"
                            + "  </BODY>\n"
                            + "</HTML>";
    File routeFile = new File(publicDirectoryFullPath + "/hi_everyone.html");
    byte[] actualResultInBytes = responseBody.get(routeFile, "everyone=someone");
    String actualResult = new String(actualResultInBytes);
    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void testBodyWithTwoKeyValuePairs() throws IOException {
    String expectedResult = "<HTML>\n"
        + "  <HEAD>\n"
        + "  </HEAD>\n"
        + "  <BODY>\n"
        + "    Bye, someone!\n"
        + "  </BODY>\n"
        + "</HTML>";
    File routeFile = new File(publicDirectoryFullPath + "/hi_everyone.html");
    byte[] actualResultInBytes = responseBody.get(routeFile, "everyone=someone&Hi=Bye");
    String actualResult = new String(actualResultInBytes);
    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void testDirectoryRoute() throws IOException {
    File routeFile = publicDirectoryFullPath;
    responseBody.get(routeFile, "");
    assertTrue(responseBody.bodyString.contains("/celebrate.gif"));
  }

  @Test
  public void testBodyWithImage() throws IOException {
    File routeFile = new File(publicDirectoryFullPath, "celebrate.gif");
    byte[] expectedResult = readFileToBytes(routeFile);
    byte[] actualResult = responseBody.get(routeFile, null);
    assertArrayEquals(expectedResult, actualResult);
  }

  private byte[] readFileToBytes(File routeFile) throws IOException {
    InputStream inputStream = new FileInputStream(routeFile);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    int chr;

    while ((chr = inputStream.read()) != -1)
      outputStream.write(chr);

    return outputStream.toByteArray();
  }

}