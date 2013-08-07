package http;

import java.io.*;
import java.util.HashMap;

public abstract class Importer {
  public HashMap importFile(String fileName) throws IOException {
    File workingDirectory = new File(System.getProperty("user.dir"));
    File csvFile = new File(workingDirectory, fileName);
    String csvData = readFile(csvFile);
     String[] csvLines = parseFile(csvData);
    return parseLines(csvLines);
  }

  //  TODO:  this code is starting to be duplicated in multiple places.  Need to fix.
  private String readFile(File templateFile) throws IOException {
    InputStream inputStream = new FileInputStream(templateFile);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    int chr;

    while ((chr = inputStream.read()) != -1)
      outputStream.write(chr);

    return outputStream.toString();
  }

  private String[] parseFile(String csvData) {
    return csvData.split("\\r?\\n");
  }

  public abstract HashMap parseLines(String[] csvLines);

  public abstract String characterDelimiter();
}