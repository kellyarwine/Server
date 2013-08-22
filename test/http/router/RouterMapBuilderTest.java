package http.router;

import http.response.routeType.Directory;
import http.response.routeType.FileNotFound;
import http.response.routeType.Public;
import http.response.routeType.Redirect;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;
import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class RouterMapBuilderTest {
  private DefaultHashMap actualResult;
  private File publicDirectoryFullPath;

  @Before
  public void setUp() throws IOException {
    File workingDirectory = new File(System.getProperty("user.dir"));
    publicDirectoryFullPath = new File(workingDirectory, "test/public/");
    deleteDirectory(new File(publicDirectoryFullPath, "/templates"));
    RouterMapBuilder routerMapBuilder = new RouterMapBuilder();
    actualResult = routerMapBuilder.buildFrom(workingDirectory, "test/public/", "routes.csv", ".htaccess");
  }

  @After
  public void tearDown() {
    deleteDirectory(new File(publicDirectoryFullPath, "/templates"));
  }

  @Test
  public void one() {
    String route = "/images";
    ArrayList actualArrayList = getActualValues(route);
    assertEquals(actualArrayList.get(0), new File(publicDirectoryFullPath, "templates/file_directory.html"));
    assertThat(actualArrayList.get(1), instanceOf(Directory.class));
  }

  @Test
  public void two() {
    String route = "/stylesheets";
    ArrayList actualArrayList = getActualValues(route);
    assertEquals(actualArrayList.get(0), new File(publicDirectoryFullPath, "templates/file_directory.html"));
    assertThat(actualArrayList.get(1), instanceOf(Directory.class));
  }

  @Test
  public void three() {
    String route = "/templates";
    ArrayList actualArrayList = getActualValues(route);
    assertEquals(actualArrayList.get(0), new File(publicDirectoryFullPath, "templates/file_directory.html"));
    assertThat(actualArrayList.get(1), instanceOf(Directory.class));
  }

  @Test
  public void four() {
    String route = "/test_directory";
    ArrayList actualArrayList = getActualValues(route);
    assertEquals(actualArrayList.get(0), new File(publicDirectoryFullPath, "templates/file_directory.html"));
    assertThat(actualArrayList.get(1), instanceOf(Directory.class));
  }

  @Test
  public void five() {
    String route = "/celebrate.gif";
    ArrayList actualArrayList = getActualValues(route);
    assertEquals(actualArrayList.get(0), new File(publicDirectoryFullPath, route));
    assertThat(actualArrayList.get(1), instanceOf(Public.class));
  }

  @Test
  public void six() {
    String route = "/color_picker.html";
    ArrayList actualArrayList = getActualValues(route);
    assertEquals(actualArrayList.get(0), new File(publicDirectoryFullPath, route));
    assertThat(actualArrayList.get(1), instanceOf(Public.class));
  }

  @Test
  public void seven() {
    String route = "/color_picker_post.html";
    ArrayList actualArrayList = getActualValues(route);
    assertEquals(actualArrayList.get(0), new File(publicDirectoryFullPath, route));
    assertThat(actualArrayList.get(1), instanceOf(Public.class));
  }

  @Test
  public void eight() {
    String route = "/color_picker_result.html";
    ArrayList actualArrayList = getActualValues(route);
    assertEquals(actualArrayList.get(0), new File(publicDirectoryFullPath, route));
    assertThat(actualArrayList.get(1), instanceOf(Public.class));
  }

  @Test
  public void nine() {
    String route = "/favicon.ico";
    ArrayList actualArrayList = getActualValues(route);
    assertEquals(actualArrayList.get(0), new File(publicDirectoryFullPath, route));
    assertThat(actualArrayList.get(1), instanceOf(Public.class));
  }

  @Test
  public void ten() {
    String route = "/favicon1.ico";
    ArrayList actualArrayList = getActualValues(route);
    assertEquals(actualArrayList.get(0), new File(publicDirectoryFullPath, route));
    assertThat(actualArrayList.get(1), instanceOf(Public.class));
  }

  @Test
  public void eleven() {
    String route = "/hi_everyone.html";
    ArrayList actualArrayList = getActualValues(route);
    assertEquals(actualArrayList.get(0), new File(publicDirectoryFullPath, route));
    assertThat(actualArrayList.get(1), instanceOf(Public.class));
  }

  @Test
  public void twelve() {
    String route = "/index.html";
    ArrayList actualArrayList = getActualValues(route);
    assertEquals(actualArrayList.get(0), new File(publicDirectoryFullPath, route));
    assertThat(actualArrayList.get(1), instanceOf(Public.class));
  }

  @Test
  public void thirteen() {
    String route = "/my_little_pony.png";
    ArrayList actualArrayList = getActualValues(route);
    assertEquals(actualArrayList.get(0), new File(publicDirectoryFullPath, route));
    assertThat(actualArrayList.get(1), instanceOf(Public.class));
  }

  @Test
  public void fourteen() {
    String route = "/.DS_Store";
    ArrayList actualArrayList = getActualValues(route);
    assertEquals(actualArrayList.get(0), new File(publicDirectoryFullPath, route));
    assertThat(actualArrayList.get(1), instanceOf(Public.class));
  }

  @Test
  public void fifteen() {
    String route = "/templates/parameters.html";
    ArrayList actualArrayList = getActualValues(route);
    assertEquals(actualArrayList.get(0), new File(publicDirectoryFullPath, route));
    assertThat(actualArrayList.get(1), instanceOf(Public.class));
  }

  @Test
  public void sixteen() {
    String route = "/punky_brewster.jpg";
    ArrayList actualArrayList = getActualValues(route);
    assertEquals(actualArrayList.get(0), new File(publicDirectoryFullPath, route));
    assertThat(actualArrayList.get(1), instanceOf(Public.class));
  }

  @Test
  public void seventeen() {
    String route = "/rainbow_brite.jpeg";
    ArrayList actualArrayList = getActualValues(route);
    assertEquals(actualArrayList.get(0), new File(publicDirectoryFullPath, route));
    assertThat(actualArrayList.get(1), instanceOf(Public.class));
  }

  @Test
  public void eighteen() {
    String route = "/the_goal.html";
    ArrayList actualArrayList = getActualValues(route);
    assertEquals(actualArrayList.get(0), new File(publicDirectoryFullPath, route));
    assertThat(actualArrayList.get(1), instanceOf(Public.class));
  }

  @Test
  public void nineteen() {
    String route = "/the_goal.txt";
    ArrayList actualArrayList = getActualValues(route);
    assertEquals(actualArrayList.get(0), new File(publicDirectoryFullPath, route));
    assertThat(actualArrayList.get(1), instanceOf(Public.class));
  }

  @Test
  public void twenty() {
    String route = "/celebrate";
    ArrayList actualArrayList = getActualValues(route);
    assertEquals(actualArrayList.get(0), new File(publicDirectoryFullPath, "/celebrate.gif"));
    assertThat(actualArrayList.get(1), instanceOf(Public.class));
  }

  @Test
  public void twentyOne() {
    String route = "/my_little_pony";
    ArrayList actualArrayList = getActualValues(route);
    assertEquals(actualArrayList.get(0), new File(publicDirectoryFullPath, "/my_little_pony.png"));
    assertThat(actualArrayList.get(1), instanceOf(Public.class));
  }

  @Test
  public void twentyTwo() {
    String route = "/rainbow";
    ArrayList actualArrayList = getActualValues(route);
    assertEquals(actualArrayList.get(0), new File(publicDirectoryFullPath, "/rainbow_brite.jpeg"));
    assertThat(actualArrayList.get(1), instanceOf(Public.class));
  }

  @Test
  public void twentyThree() {
    String route = "/punky";
    ArrayList actualArrayList = getActualValues(route);
    assertEquals(actualArrayList.get(0), new File(publicDirectoryFullPath, "/punky_brewster.jpg"));
    assertThat(actualArrayList.get(1), instanceOf(Public.class));
  }

  @Test
  public void twentyFour() {
    String route = "/parameters";
    ArrayList actualArrayList = getActualValues(route);
    assertEquals(actualArrayList.get(0), new File(publicDirectoryFullPath, "/templates/parameters.html"));
    assertThat(actualArrayList.get(1), instanceOf(Public.class));
  }

  @Test
  public void twentyFive() {
    String route = "/redirect";
    ArrayList actualArrayList = getActualValues(route);
    assertEquals(actualArrayList.get(0), new File(publicDirectoryFullPath, "/"));
    assertThat(actualArrayList.get(1), instanceOf(Redirect.class));
  }

  @Test
  public void twentySix() {
    String route = "/another_redirect";
    ArrayList actualArrayList = getActualValues(route);
    assertEquals(actualArrayList.get(0), new File(publicDirectoryFullPath, "/hi_everyone.html"));
    assertThat(actualArrayList.get(1), instanceOf(Redirect.class));
  }
//  TODO:  Add more tests at very end of project for other public files.
  @Test
  public void twentySeven() {
    assertEquals(36, actualResult.size());
  }

  @Test
  public void valid404Path() throws IOException {
    String route = "/this_url_does_not_exist";
    ArrayList actualArrayList = getActualValues(route);
    assertEquals(actualArrayList.get(0), new File(publicDirectoryFullPath, "/templates/404.html"));
    assertThat(actualArrayList.get(1), instanceOf(FileNotFound.class));
  }

  private ArrayList getActualValues(String route) {
    return (ArrayList)actualResult.get(route);
  }

  public byte[] toBytes(File routeFile) throws IOException {
    InputStream inputStream = new FileInputStream(routeFile);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    int chr;

    while ((chr = inputStream.read()) != -1)
      outputStream.write(chr);

    return outputStream.toByteArray();
  }

  public void deleteDirectory(File directory)
  {
    if (directory.isDirectory()) {
      String[] children = directory.list();
      for (int i=0; i<children.length; i++) {
        deleteDirectory(new File(directory, children[i]));
      }
    }
    directory.delete();
  }
}