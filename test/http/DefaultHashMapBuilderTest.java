package http;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

@RunWith(JUnit4.class)
public class DefaultHashMapBuilderTest{

  @Test
  public void testEmptyHashMap(){
    DefaultHashMapBuilder builder = new DefaultHashMapBuilder("default");
    HashMap map = builder.buildFrom(new HashMap());
    assertTrue(map.isEmpty());
  }

  @Test
  public void testEmptyHasADefault(){
    DefaultHashMapBuilder builder = new DefaultHashMapBuilder("default");
    HashMap map = builder.buildFrom(new HashMap());
    assertEquals("default", map.get("value"));
  }

  @Test
  public void testOriginals(){
    HashMap originalMap = new HashMap();
    originalMap.put("a", "value");

    DefaultHashMapBuilder builder = new DefaultHashMapBuilder("default");
    HashMap map = builder.buildFrom(originalMap);

    assertEquals("value", map.get("a"));
    assertEquals("default", map.get("b"));
  }

}
