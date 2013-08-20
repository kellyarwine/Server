package http.response.code;

import org.junit.Before;
import org.junit.Test;
import java.io.*;

import static org.junit.Assert.assertArrayEquals;

public class ResponseBodyTest {
  private String workingDirectory;
  private File publicDirectoryFullPath;
  private ResponseBody responseBody;

  @Before
  public void setUp() {
    workingDirectory = System.getProperty("user.dir");
    publicDirectoryFullPath = new File(workingDirectory, "test/public/");
    responseBody = new ResponseBody();
  }

  @Test
  public void getTextFile() throws IOException {
    File routeFile = new File(publicDirectoryFullPath, "the_goal.txt");
    byte[] expectedResult = toBytes(routeFile);
    byte[] actualResult = responseBody.build(routeFile);
    assertArrayEquals(expectedResult, actualResult);
  }

  @Test
  public void getHtmlFile() throws IOException {
    File routeFile = new File(publicDirectoryFullPath, "hi_everyone.html");
    byte[] expectedResult = toBytes(routeFile);
    byte[] actualResult = responseBody.build(routeFile);
    assertArrayEquals(expectedResult, actualResult);
  }

  @Test
  public void getImageFile() throws IOException {
    File routeFile = new File(publicDirectoryFullPath, "celebrate.gif");
    byte[] expectedResult = toBytes(routeFile);
    byte[] actualResult = responseBody.build(routeFile);
    assertArrayEquals(expectedResult, actualResult);
  }

  public byte[] toBytes(File routeFile) throws IOException {
    InputStream inputStream = new FileInputStream(routeFile);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    int chr;

    while ((chr = inputStream.read()) != -1)
      outputStream.write(chr);

    return outputStream.toByteArray();
  }
}