package http.server.logger;

import java.io.*;

public class FileLogger extends Logger {
  private String SERVER_LOG = "server.log";
  private File workingDirectory;

  public FileLogger(File workingDirectory) throws IOException {
    this.workingDirectory = workingDirectory;
  }

  public void out(String message) throws IOException {
    File logFile = new File(workingDirectory, SERVER_LOG);
    FileOutputStream fos = new FileOutputStream(logFile, true);
    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos, "utf-8");
    Writer writer = new BufferedWriter(outputStreamWriter);
    writer.append(message + "\n");
    writer.close();
  }
}
