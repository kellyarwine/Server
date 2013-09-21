package http.server.logger;

import java.io.*;

public class FileLogger extends Logger {
  private File workingDirectory;
  private String SERVER_LOG = "server.log";

  public FileLogger(File workingDirectory) {
    this.workingDirectory = workingDirectory;
  }

  public void out(String message) throws IOException {
    File logFile = findOrCreateLogFile();
    FileOutputStream fos = new FileOutputStream(logFile, true);
    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos, "utf-8");
    Writer writer = new BufferedWriter(outputStreamWriter);
    writer.append(message + "\n");
    writer.close();
  }

  private File findOrCreateLogFile() throws IOException {
    File logFile = new File(workingDirectory, SERVER_LOG);
    if (!logFile.exists()) {
      logFile.createNewFile();
    }
    return logFile;
  }
}
