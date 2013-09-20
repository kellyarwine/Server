package http.server.logger;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Logger {
  public abstract void out(String message) throws IOException;

  public void logMessage(String message) throws IOException {
    out("[" + currentDateAndTime() + "] " + message);
  }

  public String currentDateAndTime() {
    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    Date date = new Date();
    return dateFormat.format(date);
  }
}