package http.server;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertEquals;

@RunWith(JUnit4.class)
public class ConfigurationsTest {
  private Configurations configurations;

  @Before
  public void setUp() {
    configurations = new Configurations();
  }

  @Test
  public void parseTypicalConfigurations() {
    String[] args = new String[]{ "-p", "5000", "-d", "/public", "-e", "production" };
    assertEquals("", configurations.get(args));
    assertEquals(5000, configurations.port);
    assertEquals("/public", configurations.publicDirectory);
    assertEquals("production", configurations.env);
  }

//  @Test
//  public void parseNoConfigurations() {
//    String[] args = new String[]{};
//    assertEquals("Hi", configurations.Get(args));
//  }
}
