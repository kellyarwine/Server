package http;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class TemplaterTest {
  private String workingDirectory;
  private String publicDirectory;
  private File publicDirectoryFullPath;
  private Templater templater;

  @Before
  public void setUp() {
    workingDirectory = System.getProperty("user.dir");
    publicDirectory = "public/";
    publicDirectoryFullPath = new File(workingDirectory, publicDirectory);
    templater = new Templater(publicDirectoryFullPath);
  }

  @Test
  public void getFileDirectoryTemplate() throws IOException {
    File routeFile = templater.publicDirectoryFullPath;
    String actualString = templater.buildTemplate(routeFile);
    assertTrue(!actualString.equals(""));
  }

  @Test
  public void getFourOhFourTemplate() throws IOException {
    File expectedFile = new File(templater.publicDirectoryFullPath, "404.html");
    String expectedString = templater.readFile(expectedFile);
    File routeFile = new File(templater.publicDirectoryFullPath, "this_file_does_not_exist.html");
    String actualString = templater.buildTemplate(routeFile);
    assertEquals(expectedString, actualString);
  }

  @Test
  public void getFileDirectoryTemplateWhenThereIsAnExtraSlashAtEndOfRoute() throws IOException {
    File routeFile = new File(publicDirectoryFullPath, "/");
    String actualString = templater.buildTemplate(routeFile);
    Assert.assertTrue(actualString.contains("/404.html"));
  }

}
