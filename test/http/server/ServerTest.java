package http.server;

import http.io.MockIo;
import http.server.serverSocket.HttpServerSocket;
import http.server.serverSocket.MockHttpServerSocket;
import http.server.socket.WebSocket;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ServerTest {
  private String NEW_LINE = "\r\n";
  private File workingDirectoryFullPath;
  private File publicDirectoryFullPath;
  private File logFile;

  @Before
  public void setUp() {
    workingDirectoryFullPath = new File(System.getProperty("user.dir"));
    String publicDirectory = "test/public/";
    publicDirectoryFullPath = new File(workingDirectoryFullPath, publicDirectory);
    logFile = new File(workingDirectoryFullPath, "output.log");
  }

  @After
  public void tearDown() {
    logFile.delete();
    deleteDirectory(new File(publicDirectoryFullPath, "/templates"));
  }

  @Test
  public void startServerWithInvalidStartCommand() throws Exception {
    ArrayList commands = new ArrayList();
    commands.add("this is not how one should start the server");
    commands.add("stop server");
    MockIo mockIo = new MockIo(commands);
    Server server = new Server(mockIo);
    server.initialize();
    String expectedResult =
          "Ninja Server Menu\n"
        + "----------------------\n"
        + "Type \"help\" to see a list of available commands.\n"
        + "\n"
        + "Ninja Server Help Menu\n"
        + "-------------------------\n"
        + "Available Commands:\n"
        + " start cob_spec  Starts the server with cob_spec configurations.\n"
        + " status          Lists the status of the server.\n"
        + " stop server     Stops the server.\n"
        + " exit            Exits the application.\n"
        + " help            Provides instructions and detailed information for each command.\n"
        + "\n"
        + "Starting the Server:\n"
        + " start server    Starts the server.  The application takes six optional parameters:\n"
        + "                 an environment setting; \"test\" or \"production\" (denoted by the \"-e\" flag)\n"
        + "                 a port number (denoted by the \"-p\" flag)\n"
        + "                 the absolute path to the working directory (denoted by the \"-w\" flag)\n"
        + "                 the relative path to the public directory (denoted by the \"-d\" flag)\n"
        + "                 the Routes filename; file must exist in the root working directory (denoted by the \"-r\" flag)\n"
        + "                 the .htaccess filename; file must exist in the root working directory (denoted by the \"-h\" flag)\n"
        + "Default Server Configurations:\n"
        + " start server    [=<-e production>]\n"
        + "                 [=<-p 5000>]\n"
        + "                 [=<-w " + workingDirectoryFullPath + ">]\n"
        + "                 [=<-d test/public/>]\n"
        + "                 [=<-r test/routes.csv>]\n"
        + "                 [=<-h test/.htaccess>]\n"
        + "Ninja Server is not currently running.\n";
    assertEquals(expectedResult, readLog());
  }

  @Test
  public void displayHelpMenu() throws Exception {
    ArrayList commands = new ArrayList();
    commands.add("help");
    commands.add("stop server");
    MockIo mockIo = new MockIo(commands);
    Server server = new Server(mockIo);
    server.initialize();
    String expectedResult =
        "Ninja Server Menu\n"
            + "----------------------\n"
            + "Type \"help\" to see a list of available commands.\n"
            + "\n"
            + "Ninja Server Help Menu\n"
            + "-------------------------\n"
            + "Available Commands:\n"
            + " start cob_spec  Starts the server with cob_spec configurations.\n"
            + " status          Lists the status of the server.\n"
            + " stop server     Stops the server.\n"
            + " exit            Exits the application.\n"
            + " help            Provides instructions and detailed information for each command.\n"
            + "\n"
            + "Starting the Server:\n"
            + " start server    Starts the server.  The application takes six optional parameters:\n"
            + "                 an environment setting; \"test\" or \"production\" (denoted by the \"-e\" flag)\n"
            + "                 a port number (denoted by the \"-p\" flag)\n"
            + "                 the absolute path to the working directory (denoted by the \"-w\" flag)\n"
            + "                 the relative path to the public directory (denoted by the \"-d\" flag)\n"
            + "                 the Routes filename; file must exist in the root working directory (denoted by the \"-r\" flag)\n"
            + "                 the .htaccess filename; file must exist in the root working directory (denoted by the \"-h\" flag)\n"
            + "Default Server Configurations:\n"
            + " start server    [=<-e production>]\n"
            + "                 [=<-p 5000>]\n"
            + "                 [=<-w " + workingDirectoryFullPath + ">]\n"
            + "                 [=<-d test/public/>]\n"
            + "                 [=<-r test/routes.csv>]\n"
            + "                 [=<-h test/.htaccess>]\n"
            + "Ninja Server is not currently running.\n";
    assertEquals(expectedResult, readLog());
  }

  @Test
  public void startServerWithUnavailablePort() throws Exception {
    ServerSocket serverSocket = new ServerSocket(5001);
    ArrayList commands = new ArrayList();
    commands.add("start server -p 5001");
    commands.add("stop server");
    MockIo mockIo = new MockIo(commands);
    Server server = new Server(mockIo);
    server.initialize();
    String expectedResult =
        "Ninja Server Menu\n"
            + "----------------------\n"
            + "Type \"help\" to see a list of available commands.\n"
            + "Port 5001 is already in use.  Please try again.\n"
            + "Ninja Server is not currently running.\n";
    assertEquals(expectedResult, readLog());
    serverSocket.close();
  }

  @Test
  public void startServerWithInvalidPortNumber() throws Exception {
    ArrayList commands = new ArrayList();
    commands.add("start server -p this_is_not_a_number");
    commands.add("stop server");
    MockIo mockIo = new MockIo(commands);
    Server server = new Server(mockIo);
    server.initialize();
    String expectedResult =
        "Ninja Server Menu\n"
            + "----------------------\n"
            + "Type \"help\" to see a list of available commands.\n"
            + "Port this_is_not_a_number is not a valid port.  Please try again.\n"
            + "Ninja Server is not currently running.\n";
    assertEquals(expectedResult, readLog());
  }

  @Test
  public void startServerWithInvalidEnv() throws Exception {
    ArrayList commands = new ArrayList();
    commands.add("start server -e not_production_and_not_test");
    commands.add("stop server");
    MockIo mockIo = new MockIo(commands);
    Server server = new Server(mockIo);
    server.initialize();
    String expectedResult =
        "Ninja Server Menu\n"
            + "----------------------\n"
            + "Type \"help\" to see a list of available commands.\n"
            + "Invalid \"env\" setting.  Please try again.\n"
            + "Ninja Server is not currently running.\n";
    assertEquals(expectedResult, readLog());
  }

  @Test
  public void startServerWithInvalidWorkingDirectory() throws Exception {
    ArrayList commands = new ArrayList();
    commands.add("start server -w not_a_valid_working_directory");
    commands.add("stop server");
    MockIo mockIo = new MockIo(commands);
    Server server = new Server(mockIo);
    server.initialize();
    String expectedResult =
        "Ninja Server Menu\n"
            + "----------------------\n"
            + "Type \"help\" to see a list of available commands.\n"
            + "The working directory does not exist.  Please try again.\n"
            + "Ninja Server is not currently running.\n";
    assertEquals(expectedResult, readLog());
  }

  @Test
  public void startServerWithInvalidPublicDirectory() throws Exception {
    ArrayList commands = new ArrayList();
    commands.add("start server -d not_a_valid_public_directory");
    commands.add("stop server");
    MockIo mockIo = new MockIo(commands);
    Server server = new Server(mockIo);
    server.initialize();
    String expectedResult =
        "Ninja Server Menu\n"
            + "----------------------\n"
            + "Type \"help\" to see a list of available commands.\n"
            + "The public directory does not exist.  Please try again.\n"
            + "Ninja Server is not currently running.\n";
    assertEquals(expectedResult, readLog());
  }

  @Test
  public void startServerWithInvalidRoutesFile() throws Exception {
    ArrayList commands = new ArrayList();
    commands.add("start server -r not_a_valid_routes_file");
    commands.add("stop server");
    MockIo mockIo = new MockIo(commands);
    Server server = new Server(mockIo);
    server.initialize();
    String expectedResult =
        "Ninja Server Menu\n"
            + "----------------------\n"
            + "Type \"help\" to see a list of available commands.\n"
            + "The routes file does not exist.  Please try again.\n"
            + "Ninja Server is not currently running.\n";
    assertEquals(expectedResult, readLog());
  }

  @Test
  public void startServerWithInvalidHtAccessFile() throws Exception {
    ArrayList commands = new ArrayList();
    commands.add("start server -h not_a_valid_htaccess_file");
    commands.add("stop server");
    MockIo mockIo = new MockIo(commands);
    Server server = new Server(mockIo);
    server.initialize();
    String expectedResult =
        "Ninja Server Menu\n"
            + "----------------------\n"
            + "Type \"help\" to see a list of available commands.\n"
            + "The .htaccess file does not exist.  Please try again.\n"
            + "Ninja Server is not currently running.\n";
    assertEquals(expectedResult, readLog());
  }

  @Test
  public void startServerWithValidConfigs() throws Exception {
    ArrayList commands = new ArrayList();
    commands.add("start server -e test");
    commands.add("stop server");
    MockIo mockIo = new MockIo(commands);
    Server server = new Server(mockIo);
    server.initialize();
    String expectedResult =
        "Ninja Server Menu\n"
            + "----------------------\n"
            + "Type \"help\" to see a list of available commands.\n"
            + "Ninja Server has been shut down.\n";
    assertEquals(expectedResult, readLog());
  }

  @Test
  public void startServerWithValidCobSpecConfigs() throws Exception {
    ArrayList commands = new ArrayList();
    commands.add("start cob_spec -e test -p 5001");
    commands.add("stop server");
    MockIo mockIo = new MockIo(commands);
    Server server = new Server(mockIo);
    server.initialize();
    String expectedResult =
        "Ninja Server Menu\n"
            + "----------------------\n"
            + "Type \"help\" to see a list of available commands.\n"
            + "Ninja Server has been shut down.\n";
    assertEquals(expectedResult, readLog());
  }

  @Test
  public void startServerDisplayStatus() throws Exception {
    ArrayList commands = new ArrayList();
    commands.add("start server -e test -p 5002");
    commands.add("status");
    commands.add("stop server");
    MockIo mockIo = new MockIo(commands);
    Server server = new Server(mockIo);
    server.initialize();
    String expectedResult =
        "Ninja Server Menu\n"
            + "----------------------\n"
            + "Type \"help\" to see a list of available commands.\n"
            + "Ninja Server is running on port 5002.\n"
            + "Ninja Server has been shut down.\n";
    assertEquals(expectedResult, readLog());
  }

  @Test
  public void displayStatus() throws Exception {
    ArrayList commands = new ArrayList();
    commands.add("status");
    commands.add("stop server");
    MockIo mockIo = new MockIo(commands);
    Server server = new Server(mockIo);
    server.initialize();
    String expectedResult =
        "Ninja Server Menu\n"
            + "----------------------\n"
            + "Type \"help\" to see a list of available commands.\n"
            + "Ninja Server is not running.\n"
            + "Ninja Server is not currently running.\n";
    assertEquals(expectedResult, readLog());
  }

//  @Test
//  public void publicRoute() throws IOException {
//    Map serverConfig;
//    serverConfig = new HashMap();
//    serverConfig.put("port", "5000");
//    ArrayList requests = new ArrayList();
//    requests.add(publicRequest());
//    requests.add(directoryRequest());
//    WebSocket webSocket = getWebSocket(requests);
//    SystemServerRequestThread systemServerThread = new SystemServerRequestThread(serverConfig, new FileLogger(workingDirectory), webSocket, new QueryStringRepository());
//    systemServerThread.run();
//    String actualResult = webSocket.out().toString();
//
//    String expectedHeaderString = "HTTP/1.1 200 OK\r\n"
//        + new Date() + "\r\n"
//        + "Server: NinjaServer 1.0" + "\r\n"
//        + "Content-type: text/html; charset=UTF-8" + "\r\n"
//        + "Content-length: 21552\r\n";
//    byte[] expectedHeader       = expectedHeaderString.getBytes();
//    byte[] expectedBody         = toBytes(new File(publicDirectoryFullPath, "/the_goal.html"));
//    byte[] expectedResult       = concatenate(new byte[][]{ expectedHeader, NEW_LINE.getBytes(), expectedBody });
//
//    assertEquals(new String(expectedResult), actualResult);
//    assertArrayEquals(expectedResult, actualResult.getBytes());
//
//    systemServerThread.run();
//    String actualResult2 = webSocket.out().toString();
//
//    String expectedHeaderString2 = "HTTP/1.1 200 OK\r\n"
//        + new Date() + "\r\n"
//        + "Server: NinjaServer 1.0" + "\r\n"
//        + "Content-type: text/html; charset=UTF-8" + "\r\n"
//        + "Content-length: 21552\r\n";
//    byte[] expectedHeader2       = expectedHeaderString2.getBytes();
//    byte[] expectedBody2         = toBytes(new File(publicDirectoryFullPath, "/the_goal.html"));
//    byte[] expectedResult2       = concatenate(new byte[][]{ expectedHeader2, NEW_LINE.getBytes(), expectedBody2 });
//
//    assertEquals(new String(expectedResult2), actualResult2);
//    assertArrayEquals(expectedResult2, actualResult2.getBytes());
//  }

  public WebSocket getWebSocket(ArrayList requests) throws IOException {
    HttpServerSocket httpServerSocket = new MockHttpServerSocket(requests);
    return httpServerSocket.accept();
  }

  public byte[] toBytes(File routeFile) throws IOException {
    InputStream inputStream = new FileInputStream(routeFile);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    int chr;

    while ((chr = inputStream.read()) != -1)
      outputStream.write(chr);

    return outputStream.toByteArray();
  }

  public byte[] concatenate(byte[][] byteArray) throws IOException {
    ByteArrayOutputStream bOutput = new ByteArrayOutputStream();
    for(int i=0; i<byteArray.length; i++) {
      bOutput.write(byteArray[i]);
    }
    return bOutput.toByteArray();
  }

  private void deleteDirectory(File directory) {
    if (directory.isDirectory()) {
      String[] children = directory.list();
      for (int i=0; i<children.length; i++) {
        deleteDirectory(new File(directory, children[i]));
      }
    }
    directory.delete();
  }

  private String readLog() throws IOException {
    InputStream inputStream = new FileInputStream(logFile);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    int chr;

    while ((chr = inputStream.read()) != -1)
      outputStream.write(chr);

    return outputStream.toString();
  }

  public String simpleRootRequest() {
    String requestHeader =
        "GET / HTTP/1.1\r\n"
            + "Host: localhost:5000\r\n"
            + "Connection: keep-alive\r\n"
            + "Content-Length: 15\r\n"
            + "Cache-Control: max-age=0\r\n"
            + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n"
            + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36\r\n"
            + "Accept-Encoding: gzip,deflate,sdch\r\n"
            + "Accept-Language: en-US,en;q=0.8\r\n"
            + "Cookie: textwrapon=false; wysiwyg=textarea\r\n";
    String requestBody =
        "";
    return requestHeader + NEW_LINE + requestBody;
  }

  public String redirectRequest() {
    String requestHeader =
        "GET /redirect HTTP/1.1\r\n"
            + "Host: localhost:5000\r\n"
            + "Connection: keep-alive\r\n"
            + "Content-Length: 15\r\n"
            + "Cache-Control: max-age=0\r\n"
            + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n"
            + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36\r\n"
            + "Accept-Encoding: gzip,deflate,sdch\r\n"
            + "Accept-Language: en-US,en;q=0.8\r\n"
            + "Cookie: textwrapon=false; wysiwyg=textarea\r\n";
    String requestBody =
        "";
    return requestHeader + NEW_LINE + requestBody;
  }

  public String fileNotFoundRequest() {
    String requestHeader =
        "GET /this_url_does_not_exist HTTP/1.1\r\n"
            + "Host: localhost:5000\r\n"
            + "Connection: keep-alive\r\n"
            + "Content-Length: 15\r\n"
            + "Cache-Control: max-age=0\r\n"
            + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n"
            + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36\r\n"
            + "Accept-Encoding: gzip,deflate,sdch\r\n"
            + "Accept-Language: en-US,en;q=0.8\r\n"
            + "Cookie: textwrapon=false; wysiwyg=textarea\r\n";
    String requestBody =
        "";
    return requestHeader + NEW_LINE + requestBody;
  }

  public String publicRequest() {
    String requestHeader =
        "GET /the_goal.html HTTP/1.1\r\n"
            + "Host: localhost:5000\r\n"
            + "Connection: keep-alive\r\n"
            + "Content-Length: 15\r\n"
            + "Cache-Control: max-age=0\r\n"
            + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n"
            + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36\r\n"
            + "Accept-Encoding: gzip,deflate,sdch\r\n"
            + "Accept-Language: en-US,en;q=0.8\r\n"
            + "Cookie: textwrapon=false; wysiwyg=textarea\r\n";
    String requestBody =
        "";
    return requestHeader + NEW_LINE + requestBody;
  }

  public String directoryRequest() {
    String requestHeader =
        "GET /the_goal.html HTTP/1.1\r\n"
            + "Host: localhost:5000\r\n"
            + "Connection: keep-alive\r\n"
            + "Content-Length: 15\r\n"
            + "Cache-Control: max-age=0\r\n"
            + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n"
            + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36\r\n"
            + "Accept-Encoding: gzip,deflate,sdch\r\n"
            + "Accept-Language: en-US,en;q=0.8\r\n"
            + "Cookie: textwrapon=false; wysiwyg=textarea\r\n";
    String requestBody =
        "";
    return requestHeader + NEW_LINE + requestBody;
  }
}