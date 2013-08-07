package http;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsoleLogger implements SystemLogger {
  public String currentDateAndTime() {
    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    Date date = new Date();
    return dateFormat.format(date);
  }

  @Override
  public void out(String message) {
    System.out.println(currentDateAndTime() + " - ");
    System.out.println(message + "\r\n");
  }
}
