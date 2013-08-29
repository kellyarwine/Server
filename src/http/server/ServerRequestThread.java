package http.server;

public interface ServerRequestThread {
  public void run();
  public boolean getDidRun();
}
