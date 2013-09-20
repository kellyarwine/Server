package http.server.logger;

import java.io.IOException;

public class LoggerFactory {
  public static Logger build(String env) throws IOException {
    if (env.equals("production"))
//      return new FileLogger();
        return new ConsoleLogger();
    else
      return new ConsoleLogger();
  }

}
