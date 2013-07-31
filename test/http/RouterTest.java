package http;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class RouterTest {

  private String publicDirectory;
  private String publicDirectoryFullPath;
  private HashMap<String, String> routes;

  @Before
  public void setUp() {
    String workingDirectory = System.getProperty("user.dir");
    publicDirectory = "public/";
    publicDirectoryFullPath = workingDirectory + "/public/";
    routes = new HashMap();
  }

  @Test
  public void filenameToValidFileRoutesToFile() {
    Router router = new Router(publicDirectory, routes);
    File expectedResult = new File(publicDirectoryFullPath, "index.html");
    assertEquals(expectedResult, router.get("/index.html"));
  }

//  @Test
//  public void testRouteToIndex() {
//    Router router = new Router(publicDirectory, routes);
//    File expectedResult = new File(publicDirectoryFullPath, "index.html");
//    assertEquals(expectedResult, router.get("/"));
//  }

  @Test
  public void testRouteToImage() {
    routes.put("/my_little_pony.png", "/my_little_pony.png");
    Router router = new Router(publicDirectory, routes);
    File expectedResult = new File(publicDirectoryFullPath, "my_little_pony.png");
    assertEquals(expectedResult, router.get("/my_little_pony.png"));
  }

  @Test
  public void testRouteToText() {
    routes.put("/the_goal.txt", "/the_goal.txt");
    Router router = new Router(publicDirectory, routes);
    File expectedResult = new File(publicDirectoryFullPath, "the_goal.txt");
    assertEquals(expectedResult, router.get("/the_goal.txt"));
  }

  @Test
  public void testRouteToInvalidFile() {
    Router router = new Router(publicDirectory, routes);
    File expectedResult = new File(publicDirectoryFullPath, "404.html");
    assertEquals(expectedResult, router.get("/donaldduck.html"));
  }

  @Test
  public void testDefaultRoute() throws Exception{
    Router router = new Router(publicDirectory, routes);
    File expectedResult = new File(publicDirectoryFullPath, "404.html");
    assertEquals(expectedResult, router.get("/something_weird"));
  }
}