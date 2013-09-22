package http.io;

import java.io.*;
import java.util.List;

public class MockIo implements Io {
  public List<String> commands;
  private int commandsIndex;
  private File workingDirectory;
  private String LOG_FILE = "output.log";

  public MockIo(List<String> commands) {
    this.commands = commands;
    workingDirectory = new File(System.getProperty("user.dir"));
  }

  public String in() throws InterruptedException {
    Thread.sleep(300);
    return commands.get(commandsIndex++);
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
    File logFile = new File(workingDirectory, LOG_FILE);
    if (!logFile.exists()) {
      logFile.createNewFile();
    }
    return logFile;
  }
}
