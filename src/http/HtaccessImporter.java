package http;

import java.util.HashMap;

public class HtaccessImporter extends Importer {

  public String characterDelimiter() {
    return " ";
  }

  public HashMap parseLines(String[] csvLines) {
    HashMap hashMap = new HashMap();
    for (int i=0; i<csvLines.length; i++) {
      String[] lineArray = csvLines[i].split(characterDelimiter());
      hashMap.put(lineArray[1].trim(), lineArray[2].trim());
    }
    return hashMap;
  }
}