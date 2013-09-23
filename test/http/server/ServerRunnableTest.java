package http.server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.util.HashMap;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class ServerRunnableTest {
  private File publicDirectoryFullPath;
  private ServerRunnable serverRunnable;
  HashMap<String, String> serverConfig;

  @Before
  public void setUp() throws IOException, URISyntaxException {
    File workingDirectory = new File(System.getProperty("user.dir"));
    publicDirectoryFullPath = new File(workingDirectory, "test/public/");
    serverConfig = new HashMap<String, String>();
    serverConfig.put("port", "5000");
    serverConfig.put("publicDirectoryPath", "test/public/");
    serverConfig.put("env", "test");
    serverConfig.put("routesFilePath", "test/routes.csv");
    serverConfig.put("htAccessFilePath", "test/.htaccess");
    serverConfig.put("workingDirectoryPath", new File(System.getProperty("user.dir")).toString());
    serverRunnable = new ServerRunnable(serverConfig);
  }

  @After
  public void tearDown() {
    deleteDirectory(new File(publicDirectoryFullPath, "/templates"));
  }

  @Test
  public void initialize() throws IOException, URISyntaxException {
    assertTrue(new File(publicDirectoryFullPath, "templates/file_directory.html").exists());
    assertFalse(serverRunnable.closeRequested);
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