package http;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class QueryStringRepositoryTest {
  private QueryStringRepository queryStringRepository;

  @Before
  public void setUp() {
    queryStringRepository = new QueryStringRepository();
  }

  @Test
  public void savePostQueryString() {
    HashMap previousQueryStringHash = new HashMap();
    previousQueryStringHash.put("one", "hi");
    previousQueryStringHash.put("two", "hey");
    previousQueryStringHash.put("three", "hello");
    queryStringRepository.saveQueryString("POST", previousQueryStringHash);
    assertEquals(previousQueryStringHash, queryStringRepository.previousQueryStringHash);
  }

  @Test
  public void savePutQueryString() {
    HashMap previousQueryStringHash = new HashMap();
    previousQueryStringHash.put("hi", "goodbye");
    previousQueryStringHash.put("hello", "c ya later");
    queryStringRepository.saveQueryString("PUT", previousQueryStringHash);
    assertEquals(previousQueryStringHash, queryStringRepository.previousQueryStringHash);
  }

  @Test
  public void doNotSaveGetQueryString() {
    HashMap previousQueryStringHash = new HashMap();
    previousQueryStringHash.put("one", "1");
    previousQueryStringHash.put("two", "2");
    previousQueryStringHash.put("three", "3");
    queryStringRepository.saveQueryString("GET", previousQueryStringHash);
    assertThat(previousQueryStringHash, not(equalTo(queryStringRepository.previousQueryStringHash)));
    assertEquals(new HashMap(), queryStringRepository.previousQueryStringHash);
  }
}