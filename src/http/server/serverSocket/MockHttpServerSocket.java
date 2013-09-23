package http.server.serverSocket;

import http.server.socket.MockSocket;
import http.server.socket.WebSocket;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class MockHttpServerSocket implements HttpServerSocket {
  ArrayList<String> mockRequests;
  private boolean isClosed;

  public MockHttpServerSocket(String mockRequestsFullPathString) throws IOException {
    isClosed = false;
    getMockRequests(new File(mockRequestsFullPathString));
  }

  public void getMockRequests(File tsvFile) throws IOException {
    String tsvText = toString(tsvFile);
    mockRequests = parseLines(tsvText);
  }

  public WebSocket accept() throws IOException {
      String request = mockRequests.remove(0);
      return new MockSocket(request);
  }

  public void close() {
    isClosed = true;
  }

  public boolean isClosed() {
    return isClosed;
  }

  public boolean isBound() {
    return false;
  }

  private String toString(File csvFileName) throws IOException {
    InputStream inputStream = new FileInputStream(csvFileName);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    int chr;
    while ((chr = inputStream.read()) != -1)
      outputStream.write(chr);
    return outputStream.toString();
  }

  private ArrayList<String> parseLines(String tsvData) {
    return new ArrayList<String>(Arrays.asList(tsvData.split("\t")));
  }
}