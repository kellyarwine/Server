package http.server.serverSocket;

import http.server.ServerThread;

public class MockServerThread implements Runnable, ServerThread {
  public boolean didRun;

  @Override
  public void run() {
    didRun = true;
  }

  public boolean getDidRun() {
    return didRun;
  }
}