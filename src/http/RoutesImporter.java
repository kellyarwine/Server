package http;

import java.util.HashMap;

public class RoutesImporter extends Importer {

  public String characterDelimiter() {
    return ",";
  }

  public HashMap parseLines(String[] lines) {
    HashMap hashMap = new HashMap();
    for (int i=0; i<lines.length; i++) {
      String line = lines[i];
      String[] lineArray = line.split(characterDelimiter());
      String element1 = lineArray[0].trim();
      String element2 = lineArray[1].trim();
      hashMap.put(element1, element2);
    }
    return hashMap;
  }
}
