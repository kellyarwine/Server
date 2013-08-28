package http.server;

public interface ServerThread {
  public void run();
  public boolean getDidRun();
}
