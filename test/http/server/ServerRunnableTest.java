package http.server;

import http.router.DefaultHashMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import static junit.framework.Assert.assertTrue;

public class ServerRunnableTest {
  private DefaultHashMap actualResult;
  private File publicDirectoryFullPath;

  @Before
  public void setUp() throws IOException, URISyntaxException {
    File workingDirectory = new File(System.getProperty("user.dir"));
    publicDirectoryFullPath = new File(workingDirectory, "test/public/");
    HashMap serverConfig = new HashMap();
    serverConfig.put("port", "5000");
    serverConfig.put("publicDirectoryPath", "test/public/");
    serverConfig.put("env", "production");
    serverConfig.put("routesFilePath", "test/routes.csv");
    serverConfig.put("htAccessFilePath", "test/.htacess");
    serverConfig.put("workingDirectoryPath", new File(System.getProperty("user.dir")).toString());
    ServerRunnable serverRunnable = new ServerRunnable(serverConfig);
  }

  @After
  public void tearDown() {
    deleteDirectory(new File(publicDirectoryFullPath, "/templates"));
  }

  @Test
  public void fileDirectoryTemplateExists() {
    String route = "/templates/404.html";
    assertTrue(new File(publicDirectoryFullPath, "templates/file_directory.html").exists());
  }

  public void deleteDirectory(File directory)
  {
    if (directory.isDirectory()) {
      String[] children = directory.list();
      for (int i=0; i<children.length; i++) {
        deleteDirectory(new File(directory, children[i]));
      }
    }
    directory.delete();
  }
}
