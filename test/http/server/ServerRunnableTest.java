package http.server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class ServerRunnableTest {
  private File publicDirectoryFullPath;
  private File mockRequestsFile;

  @Before
  public void setUp() throws IOException {
    File workingDirectory = new File(System.getProperty("user.dir"));
    publicDirectoryFullPath = new File(workingDirectory, "test/public/");
    mockRequestsFile = new File(workingDirectory, "test/mock_requests.tsv");
  }

  @After
  public void tearDown() {
    deleteDirectory(new File(publicDirectoryFullPath, "/templates"));
    mockRequestsFile.delete();
  }

  @Test
  public void initialize() throws IOException {
    HashMap<String, String> serverConfig = new HashMap<String, String>();
    serverConfig.put("port", "5000");
    serverConfig.put("publicDirectoryPath", "test/public/");
    serverConfig.put("env", "test");
    serverConfig.put("routesFilePath", "test/routes.csv");
    serverConfig.put("htAccessFilePath", "test/.htaccess");
    serverConfig.put("workingDirectoryPath", new File(System.getProperty("user.dir")).toString());
    ServerRunnable serverRunnable = new ServerRunnable(serverConfig);
    assertTrue(new File(publicDirectoryFullPath, "templates/file_directory.html").exists());
    assertFalse(serverRunnable.closeRequested);
  }

  @Test
  public void throwsIoException() throws IOException {
    HashMap<String, String> serverConfig = new HashMap<String, String>();
    serverConfig.put("port", "5000");
    serverConfig.put("publicDirectoryPath", "test/public/");
    serverConfig.put("env", "test");
    serverConfig.put("routesFilePath", "test/routes.csv");
    serverConfig.put("htAccessFilePath", "test/.htaccess");
    serverConfig.put("workingDirectoryPath", new File(System.getProperty("user.dir")).toString());
    serverConfig.put("mockRequestsFilePath", "test/mock_requests.tsv");
    ServerRunnable serverRunnable = new ServerRunnable(serverConfig);
    createMockRequestsFile();
    new File(System.getProperty("user.dir"), "server.log").delete();
    assertFalse(serverRunnable.exceptionThrown);
    serverRunnable.run();
    assertTrue(serverRunnable.exceptionThrown);

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

  private void createMockRequestsFile() throws IOException {
    mockRequestsFile.createNewFile();
  }
}