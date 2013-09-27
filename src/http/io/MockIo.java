package http.io;

import java.io.*;
import java.util.List;

public class MockIo implements Io {
  public List<String> input;
  private int inputIndex;
  private File workingDirectory;
  private String LOG_FILE = "server.log";

  public MockIo(List<String> input) {
    this.input = input;
    workingDirectory = new File(System.getProperty("user.dir"));
  }

  public String in() throws InterruptedException {
    return input.get(inputIndex++);
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
