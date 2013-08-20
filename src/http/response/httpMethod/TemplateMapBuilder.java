package http.response.httpMethod;

import http.response.code.FourHundredFive;
import http.response.code.TwoHundred;

import java.util.HashMap;

public class TemplateMapBuilder {

  public HashMap build() {
    HashMap templateMap = new HashMap();
    templateMap.put(true,  new TwoHundred());
    templateMap.put(false, new FourHundredFive());
    return templateMap;
  }
}