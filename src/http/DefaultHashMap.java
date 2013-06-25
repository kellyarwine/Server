package http;


import java.util.HashMap;

public class DefaultHashMap extends HashMap{
  private String defaultValue;

  public DefaultHashMap(String defaultValue){
    this.defaultValue = defaultValue;
  }

  public Object get(java.lang.Object o){
    if(hasNoKey(o))
        return defaultValue;
    else
      return super.get(o);
  }

  private boolean hasNoKey(Object o) {
    return super.get(o) == null;
  }

}