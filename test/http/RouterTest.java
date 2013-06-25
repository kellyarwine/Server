package http;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class RouterTest {

  private String publicDirectory;
  private HashMap<String, String> routes;

  @Before
  public void setUp() {
    routes = new DefaultHashMap(Router.NOT_FOUND);
    publicDirectory = "public";
  }

  @Test
  public void filenameToValidFileRoutesToFile() {
    Router router = new Router(publicDirectory, routes);
    assertEquals(publicDirectory + "/index.html", router.get("/index.html"));
  }

  @Test
  public void testRouteToIndex() {
    Router router = new Router(publicDirectory, routes);
    assertEquals(publicDirectory + "/index.html", router.get("/"));
  }

  @Test
  public void testRouteToImage() {
    routes.put("/my_little_pony.png", "/my_little_pony.png");
    Router router = new Router(publicDirectory, routes);
    assertEquals(publicDirectory + "/my_little_pony.png", router.get("/my_little_pony.png"));
  }

  @Test
  public void testRouteToText() {
    routes.put("/the_goal.txt", "/the_goal.txt");
    Router router = new Router(publicDirectory, routes);
    assertEquals(publicDirectory + "/the_goal.txt", router.get("/the_goal.txt"));
  }

  @Test
  public void testRouteToInvalidFile() {
    Router router = new Router(publicDirectory, routes);

    assertEquals(publicDirectory + "/404.html", router.get("/donaldduck.html"));
  }

}

