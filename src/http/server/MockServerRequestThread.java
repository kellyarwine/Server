package http.server;

public class MockServerRequestThread implements Runnable, ServerRequestThread {
  public boolean didRun;

  @Override
  public void run() {
    didRun = true;
  }

  public boolean getDidRun() {
    return didRun;
  }
}