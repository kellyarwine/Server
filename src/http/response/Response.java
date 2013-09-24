package http.response;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;

public class Response {
  public void send(OutputStream out, byte[] builtResponse) throws IOException, ParseException {
    out.write(builtResponse);
    out.flush();
  }
}
