package http.router.csvMapBuilder;

import java.io.*;
import java.util.HashMap;

public abstract class CsvMapBuilder {
  public File publicDirectoryFullPath;

  public CsvMapBuilder(File publicDirectoryFullPath) {
    this.publicDirectoryFullPath = publicDirectoryFullPath;
  }

  public HashMap get(File csvFile) throws IOException {
    String csvText = toBytes(csvFile);
    String[] csvLines = parseLines(csvText);
    return createMap(csvLines);
  }

  private String toBytes(File csvFileName) throws IOException {
    InputStream inputStream = new FileInputStream(csvFileName);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    int chr;
    while ((chr = inputStream.read()) != -1)
      outputStream.write(chr);
    return outputStream.toString();
  }

  private String[] parseLines(String csvData) {
    return csvData.split("\\r?\\n");
  }

  public abstract HashMap createMap(String[] csvLines);
}