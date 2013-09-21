package http.server.logger;

public class ConsoleLogger extends Logger {
  public void out(String message) {
    System.out.println(message);
  }
}