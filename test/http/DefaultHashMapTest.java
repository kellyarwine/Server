package http;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertEquals;

@RunWith(JUnit4.class)
public class DefaultHashMapTest {

  private DefaultHashMap defaultHashMap;

  @Before
  public void setUp() {
    defaultHashMap = new DefaultHashMap("I am the default value.");
    defaultHashMap.put("Kelly", "Steensma");
  }

  @Test
  public void testKeyExists() {
    assertEquals(defaultHashMap.get("Kelly"), "Steensma");
  }

  @Test
  public void testKeyDoesNotExist() {
    assertEquals(defaultHashMap.get("Donald"), "I am the default value.");
  }

}