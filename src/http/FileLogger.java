package http;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileLogger implements SystemLogger {
  private File findOrCreateLogFile() throws IOException {
    String workingDirectory = System.getProperty("user.dir");
    File logFile = new File(workingDirectory + "/server.log");
    if (!logFile.exists()) {
      logFile.createNewFile();
    }
    return logFile;
  }

  public String currentDateAndTime() {
    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    Date date = new Date();
    return dateFormat.format(date);
  }

  @Override
  public void out(String message) throws IOException {
    File logFile = findOrCreateLogFile();
    FileOutputStream fos = new FileOutputStream(logFile, true);
    OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
    Writer writer = new BufferedWriter(osw);
    writer.append(currentDateAndTime()+" - ");
    writer.append(message + "\r\n");
    writer.close();
  }
}
