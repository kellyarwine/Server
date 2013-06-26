package http;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static junit.framework.Assert.assertEquals;

@RunWith(JUnit4.class)
public class SafeBufferedReaderTest {
  private Reader reader;
  private SafeBufferedReader safeReader;
  private StringBuffer buffer;

  @Before
  public void setUp() throws IOException {
    reader = new StringReader("This is some text.");
    safeReader = new SafeBufferedReader(reader);
    buffer = new StringBuffer(100);
  }

  @Test
  public void testRead() throws IOException {
    int chr = safeReader.read();

    while (true) {
      if (chr == -1)
        break;

      buffer.append(chr);
    }

    assertEquals("This is some text.", buffer.toString());
  }


}
