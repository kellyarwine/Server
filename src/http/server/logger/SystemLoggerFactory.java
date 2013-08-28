package http.server.logger;

import java.io.IOException;

public class SystemLoggerFactory {
  public static SystemLogger build(String env) throws IOException {
    if (env.equals("production"))
//      return new FileLogger();
        return new ConsoleLogger();
    else
      return new ConsoleLogger();
  }

}
