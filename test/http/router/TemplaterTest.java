package http.router;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertArrayEquals;

public class TemplaterTest {
  File workingDirectory;
  File publicDirectoryFullPath;
  Templater templater;

  @Before
  public void setUp() throws IOException {
    workingDirectory = new File(System.getProperty("user.dir"));
    publicDirectoryFullPath = new File(workingDirectory, "test/public/");
    templater = new Templater(publicDirectoryFullPath);
  }

  @After
  public void tearDown() {
    deleteDirectory(new File(publicDirectoryFullPath, "/templates"));
  }

  @Test
  public void FourHundredFourTemplate() throws IOException {
    templater.createTemplate("404.html");
    byte[] actualResult = toBytes(new File(publicDirectoryFullPath, "templates/404.html"));
    byte[] expectedResult = toBytes(new File(workingDirectory, "src/http/templates/404.html"));
    assertArrayEquals(expectedResult, actualResult);
  }

  @Test
  public void FileDirectoryTemplate() throws IOException {
    templater.createTemplate("file_directory.html");
    byte[] actualResult = toBytes(new File(publicDirectoryFullPath, "templates/file_directory.html"));
    byte[] expectedResult = toBytes(new File(workingDirectory, "src/http/templates/file_directory.html"));
    assertArrayEquals(expectedResult, actualResult);
  }

  @Test
  public void FormTemplate() throws IOException {
    templater.createTemplate("form.html");
    byte[] actualResult = toBytes(new File(publicDirectoryFullPath, "templates/form.html"));
    byte[] expectedResult = toBytes(new File(workingDirectory, "src/http/templates/form.html"));
    assertArrayEquals(expectedResult, actualResult);
  }

  @Test
  public void ParametersTemplate() throws IOException {
    templater.createTemplate("parameters.html");
    byte[] actualResult = toBytes(new File(publicDirectoryFullPath, "templates/parameters.html"));
    byte[] expectedResult = toBytes(new File(workingDirectory, "src/http/templates/parameters.html"));
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

  private void deleteDirectory(File directory) {
    if (directory.isDirectory()) {
      String[] children = directory.list();
      for (int i=0; i<children.length; i++) {
        deleteDirectory(new File(directory, children[i]));
      }
    }
    directory.delete();
  }
}