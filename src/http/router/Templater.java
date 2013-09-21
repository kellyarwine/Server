package http.router;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

public class Templater {
  private File publicDirectoryFullPath;

  public void copyTemplatesToDisk(String resourcesPath, File publicDirectoryFullPath) throws IOException, URISyntaxException {
    this.publicDirectoryFullPath = publicDirectoryFullPath;
    File templateDirectory = createTemplateDirectory();
    URL resources = getClass().getResource(resourcesPath);
    File jarTemplates = new File(resources.toURI());

    for (File nextFile : jarTemplates.listFiles()) {
      createTemplate(nextFile, templateDirectory);
    }
  }

  private File createTemplateDirectory(){
    File templateDirectory = new File(publicDirectoryFullPath, "/templates");
    templateDirectory.mkdir();
    return templateDirectory;
  }

  private void createTemplate(File resource, File templateDirectory) throws IOException {
    File newTemplate = new File(templateDirectory, resource.getName());
    newTemplate.createNewFile();
    readResourceToNewTemplate(resource, newTemplate);
  }

  private void readResourceToNewTemplate(File resource, File newTemplate) throws IOException {
    InputStream inputStream = new FileInputStream(resource);
    OutputStream fileOutputStream = new FileOutputStream(newTemplate);
    int length;
    byte[] buffer = new byte[1024];
    while((length = inputStream.read(buffer)) > -1) {
      fileOutputStream.write(buffer, 0, length);
    }
    inputStream.close();
  }
}