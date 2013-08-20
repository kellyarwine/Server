package http.server.logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsoleLogger extends SystemLogger {
  public String currentDateAndTime() {
    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    Date date = new Date();
    return dateFormat.format(date);
  }

  public void out(String message) {
    System.out.println(message);
  }
}