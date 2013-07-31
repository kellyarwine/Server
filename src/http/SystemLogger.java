package http;

import java.io.IOException;

public interface SystemLogger {
  public void out(String message) throws IOException;
}