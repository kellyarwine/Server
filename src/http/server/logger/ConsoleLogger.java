package http.server.logger;

public class ConsoleLogger extends SystemLogger {
  public void out(String message) {
    System.out.println(message);
  }
}