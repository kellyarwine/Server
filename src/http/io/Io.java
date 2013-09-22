package http.io;

import java.io.IOException;

public interface Io {
  public String in() throws InterruptedException;
  public void out(String message) throws IOException;
}
