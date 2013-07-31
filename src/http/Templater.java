package http;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Templater {
  public File publicDirectoryFullPath;

  public Templater(File publicDirectoryFullPath) {
    this.publicDirectoryFullPath = publicDirectoryFullPath;
  }

  public String buildTemplate(File routeFile) throws IOException {
    File modifiedRouteFile = trimTrailingForwardSlash(routeFile);
    return getTemplate(modifiedRouteFile);
  }

  private File trimTrailingForwardSlash(File routeFile) {
    if (routeFile.toString().endsWith("/")) {
      String routeFileString = routeFile.toString();
      String modifiedRouteFileString = routeFileString.substring(0, routeFileString.length()-1);
      return new File(modifiedRouteFileString);
    }
    else
      return routeFile;
  }

  public String getTemplate(File modifiedRouteFile) throws IOException {
    if (modifiedRouteFile.isDirectory())
      return getFileDirectoryTemplate(modifiedRouteFile);
    else
      return getFourOhFourTemplate();
  }

  public String getFileDirectoryTemplate(File routeFile) throws IOException {
    String templateString = readInputStreamToString(this.getClass().getResourceAsStream("/http/templates/file_directory.html"));
      HashMap templateElements = getFileDirectoryTemplateElements(routeFile);
    return updateTemplateWithKeyValuePairs(templateString, templateElements);
  }

  public String getFourOhFourTemplate() throws IOException {
    return readInputStreamToString(this.getClass().getResourceAsStream("/http/templates/404.html"));
  }

  private HashMap getFileDirectoryTemplateElements(File routeFile) throws IOException {
    HashMap elements = new HashMap();
    String folderNameKeyValue = getFolderNameKeyValuePair(routeFile);
    elements.put("folder_name", folderNameKeyValue);
    String fileListKeyValue = getFileListKeyValuePair(routeFile);
    elements.put("file_list", fileListKeyValue);
    return elements;
  }

  public String getFolderNameKeyValuePair(File routeFile) throws IOException {
    return routeFile.getCanonicalPath();
  }

  public String getFileListKeyValuePair(File routeFile) {
    File[] fileList = routeFile.listFiles();
    String fileListString = "";

    for (int i=0; i<fileList.length; i++) {
      String relativePathAndFileName = subtractStrings(fileList[i].toString(), publicDirectoryFullPath.toString());
      fileListString += "<a href=\"";
      fileListString += relativePathAndFileName;
      fileListString += "\">";
      fileListString += relativePathAndFileName;
      fileListString += "</a><br>\r\n";
    }
    return fileListString;
  }

  private String subtractStrings(String stringOriginal, String stringToRemove) {
    return stringOriginal.replaceAll(stringToRemove,"");
  }

  private String updateTemplateWithKeyValuePairs(String templateString, HashMap templateElements) {
    Set set = templateElements.entrySet();

    for(Iterator i = set.iterator(); i.hasNext();) {
      Map.Entry element = (Map.Entry) i.next();
      String key = (String) element.getKey();
      String value = (String) element.getValue();
      templateString = findAndReplace(templateString, key, value);
    }
    return templateString;
  }

  private String findAndReplace(String templateString, String key, String value) {
    return templateString.replaceAll(key, value);
  }

  public String readFile(File templateFile) throws IOException {
    InputStream inputStream = new FileInputStream(templateFile);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    int chr;

    while ((chr = inputStream.read()) != -1)
      outputStream.write(chr);

    return outputStream.toString();
  }

  public String readInputStreamToString(InputStream inputStream) throws IOException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    int chr;

    while ((chr = inputStream.read()) != -1)
      outputStream.write(chr);

    return outputStream.toString();
  }
}
