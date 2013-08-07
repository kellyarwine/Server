package http;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static junit.framework.Assert.assertEquals;

public class HtaccessImporterTest {
  @Test
  public void importsData() throws IOException {
    HtaccessImporter htaccessImporter = new HtaccessImporter();
    HashMap actualResult = htaccessImporter.importFile(".htaccess");
    HashMap expectedResult = new HashMap();
    expectedResult.put("/redirect", "/my_little_pony.png");
    expectedResult.put("/anotherredirect", "/celebrate.gif");
    assertEquals(expectedResult, actualResult);
  }
}
