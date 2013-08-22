package http.router;

import java.io.*;

public class Templater {
  private File publicDirectoryFullPath;

  public Templater(File publicDirectoryFullPath) {
    this.publicDirectoryFullPath = publicDirectoryFullPath;
  }

  public void createTemplate(String name) throws IOException {
    File templateDirectory = createTemplateDirectory();
    File newTemplate = createTemplate(name, templateDirectory);
    readResourceToNewTemplate(name, newTemplate);
  }

  private File createTemplateDirectory(){
    File templateDirectory = new File(publicDirectoryFullPath, "/templates");
    templateDirectory.mkdir();
    return templateDirectory;
  }

  private File createTemplate(String name, File templateDirectory) throws IOException {
    File newTemplate = new File(templateDirectory, name);
    newTemplate.createNewFile();
    return newTemplate;
  }

  private void readResourceToNewTemplate(String name, File temp) throws IOException {
    InputStream inputStream = getClass().getResourceAsStream("/http/templates/" + name);
    OutputStream fileOutputStream = new FileOutputStream(temp);
    int length;
    byte[] buffer = new byte[1024];
    while((length = inputStream.read(buffer)) > -1) {
      fileOutputStream.write(buffer, 0, length);
    }
    inputStream.close();
  }
}