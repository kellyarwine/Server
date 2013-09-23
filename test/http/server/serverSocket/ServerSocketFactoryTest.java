package http.server.serverSocket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;

import static junit.framework.Assert.assertTrue;

public class ServerSocketFactoryTest {
  private ServerSocketFactory serverSocketFactory;
  private File mockRequestsFile;

  @Before
  public void setUp() {
    File workingDirectoryFullPath = new File(System.getProperty("user.dir"));
    mockRequestsFile = new File(workingDirectoryFullPath, "test/mock_requests.tsv");
    serverSocketFactory = new ServerSocketFactory();
  }

  @After
  public void tearDown() {
    mockRequestsFile.delete();
  }

  @Test
  public void testSystemLogger() throws IOException {
    HashMap<String, String> serverConfig = new HashMap<String, String>();
    serverConfig.put("port", "5000");
    serverConfig.put("publicDirectoryPath", "test/public/");
    serverConfig.put("env", "production");
    serverConfig.put("routesFilePath", "test/routes.csv");
    serverConfig.put("htAccessFilePath", "test/.htaccess");
    serverConfig.put("workingDirectoryPath", new File(System.getProperty("user.dir")).toString());
    serverConfig.put("mockRequestsFilePath", "test/mock_requests.csv");
    assertTrue(serverSocketFactory.build(serverConfig) instanceof SystemHttpServerSocket);
  }

  @Test
  public void testMockLogger() throws IOException {
    createMockRequestsTsv("");
    HashMap<String, String> serverConfig = new HashMap<String, String>();
    serverConfig.put("port", "5000");
    serverConfig.put("publicDirectoryPath", "test/public/");
    serverConfig.put("env", "test");
    serverConfig.put("routesFilePath", "test/routes.csv");
    serverConfig.put("htAccessFilePath", "test/.htaccess");
    serverConfig.put("mockRequestsFilePath", "test/mock_requests.tsv");
    assertTrue(serverSocketFactory.build(serverConfig) instanceof MockHttpServerSocket);
  }

  private void createMockRequestsTsv(String requestString) throws IOException {
    createMockRequestsFile();
    FileOutputStream fos = new FileOutputStream(mockRequestsFile, true);
    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos, "utf-8");
    Writer writer = new BufferedWriter(outputStreamWriter);
    writer.append(requestString + "\t");
    writer.close();
  }

  private void createMockRequestsFile() throws IOException {
    mockRequestsFile.createNewFile();
  }
}