package http.request;

import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;

public class QueryStringRepositoryTest {
  private QueryStringRepository queryStringRepository;

  @Before
  public void setUp() {
    queryStringRepository = new QueryStringRepository();
  }

  @Test
  public void saveAndGetQueryString() {
    String route = "/form";
    String queryString = "one=hi&two=hello&three=bye";
    queryStringRepository.save(route, queryString);

    assertEquals(queryString, queryStringRepository.getQueryString(route));
  }
}