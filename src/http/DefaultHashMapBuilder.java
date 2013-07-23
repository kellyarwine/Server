package http;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DefaultHashMapBuilder {
  private String defaultValue;

  public DefaultHashMapBuilder(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  public HashMap buildFrom(HashMap originals) {
    DefaultHashMap map = new DefaultHashMap(defaultValue);
    Iterator iterator = originals.entrySet().iterator();

    while(iterator.hasNext())
    {
      Map.Entry pairs = (Map.Entry)iterator.next();
      map.put(pairs.getKey(), pairs.getValue());
    }

    return map;
  }
}
