package http.server;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Templater {
  private File publicDirectoryFullPath;

  public void copyTemplatesToDisk(String resourcesPath, File publicDirectoryFullPath) throws IOException {
    this.publicDirectoryFullPath = publicDirectoryFullPath;
    File templateDirectory = createTemplateDirectory();
    copyResourcesToTemplateDirectory(resourcesPath, templateDirectory);
  }

  private void copyResourcesToTemplateDirectory(String resourcesPath, File templateDirectory) throws IOException {
    InputStream inputStream = getClass().getResourceAsStream(resourcesPath);
    ZipInputStream zipInputStream = new ZipInputStream(inputStream);
    ZipEntry entry;
    while ((entry = zipInputStream.getNextEntry()) != null) {
      createTemplate(zipInputStream, entry, templateDirectory);
    }
    zipInputStream.close();
  }

  private File createTemplateDirectory(){
    File templateDirectory = new File(publicDirectoryFullPath, "/templates");
    templateDirectory.mkdir();
    return templateDirectory;
  }

  private void createTemplate(ZipInputStream zipInputStream, ZipEntry entry, File templateDirectory) throws IOException {
    File newTemplate = new File(templateDirectory, entry.getName());
    newTemplate.createNewFile();
    readResourceToNewTemplate(zipInputStream, newTemplate);
  }

  private void readResourceToNewTemplate(ZipInputStream zipInputStream, File newTemplate) throws IOException {
    OutputStream fileOutputStream = new FileOutputStream(newTemplate);
    int length;
    byte[] buffer = new byte[1024];
    while((length = zipInputStream.read(buffer)) > -1) {
      fileOutputStream.write(buffer, 0, length);
    }
  }
}