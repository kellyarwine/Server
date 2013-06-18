package http;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class RouterTest {

  private Router router;
  private String publicDirectory;

  @Before
  public void setUp() {
    router = new Router();
    publicDirectory = "/Users/Kelly/Desktop/Java_HTTP_Server/public";
  }

  @Test
  public void testValidFile() {
    assert(router.fileValid("/Users/Kelly/Desktop/Java_HTTP_Server/public/donaldduck.html") == false);
  }

  @Test
  public void testInvalidFile() {
    assert(router.fileValid("/Users/Kelly/Desktop/Java_HTTP_Server/public/hi_everyone.html") == true);
  }

  @Test
  public void testRouteToIndex() {
    assertEquals(router.get(publicDirectory + "/", publicDirectory), publicDirectory + "/index.html");
  }

  @Test
  public void testRouteToHiEveryone() {
    assertEquals(router.get(publicDirectory + "/hi", publicDirectory), publicDirectory + "/hi_everyone.html");
  }

  @Test
  public void testRouteToImage() {
    assertEquals(router.get(publicDirectory + "/my_little_pony.png", publicDirectory), publicDirectory + "/my_little_pony.png");
  }

  @Test
  public void testRouteToText() {
    assertEquals(router.get(publicDirectory + "/the_goal.txt", publicDirectory), publicDirectory + "/the_goal.txt");
  }

  @Test
  public void testRouteToInvalidFile() {
    assertEquals(router.get(publicDirectory + "/donaldduck.html", publicDirectory), publicDirectory + "/404.html");
  }

}

