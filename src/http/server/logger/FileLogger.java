package http.server.logger;

import java.io.*;

public class FileLogger extends SystemLogger {
  public void out(String message) throws IOException {
    File logFile = findOrCreateLogFile();
    FileOutputStream fos = new FileOutputStream(logFile, true);
    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos, "utf-8");
    Writer writer = new BufferedWriter(outputStreamWriter);
    writer.append(message + "\n");
    writer.close();
  }

  private File findOrCreateLogFile() throws IOException {
    String workingDirectory = System.getProperty("user.dir");
    File logFile = new File(workingDirectory + "/server.log");
    if (!logFile.exists()) {
      logFile.createNewFile();
    }
    return logFile;
  }
}
