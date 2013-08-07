package http;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import static junit.framework.Assert.assertEquals;

public class RoutesImporterTest {
  @Test
  public void importsData() throws IOException {
    RoutesImporter routesImporter = new RoutesImporter();
    HashMap actualResult = routesImporter.importFile("routes.csv");
    HashMap expectedResult = new HashMap();
    expectedResult.put("/celebrate", "/celebrate.gif");
    expectedResult.put("/my_little_pony", "/my_little_pony.png");
    expectedResult.put("/punky", "/punky_brewster.jpg");
    expectedResult.put("/rainbow", "/rainbow_brite.jpeg");
    expectedResult.put("/parameters", "/parameters.html");
    assertEquals(expectedResult, actualResult);
  }
}
