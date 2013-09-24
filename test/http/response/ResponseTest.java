package http.response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class ResponseTest {
  @Test
  public void send() throws IOException, ParseException {
    byte[] builtResponse = "This is some text.".getBytes();
    OutputStream out = new ByteArrayOutputStream();
    new Response().send(out, builtResponse);
    assertEquals(new String(builtResponse), out.toString());
  }
}