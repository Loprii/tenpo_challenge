package com.tenpo.challenge.services;

import com.google.common.cache.LoadingCache;
import com.tenpo.challenge.service.impl.UserCacheImpl;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserCacheITest {

  private static final String TOKEN =
      "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwicm9sZXMiOlsiVVNFUl9ST0xFIl0sImV4cCI6MTY1ODI5NTcyNywiaWF0IjoxNjU4Mjk0ODI3fQ.g-HJQ97Sj4Z3nwlwqikwkZqMXn9btdHJUmwuuZA_uS0";
  private static final String USERNAME = "user";
  private UserCacheImpl service;
  private LoadingCache<String, String> cache;

  @BeforeEach
  public void setUp() {
    service = new UserCacheImpl();
    cache = service.getCache();
  }

  @Test
  public void addUserOnCache() throws ExecutionException {
    service.addUser(TOKEN, USERNAME);

    assertEquals(USERNAME, cache.get(TOKEN));
  }

  @Test
  public void removeUserFromCache() throws ExecutionException {
    cache.put(TOKEN, USERNAME);

    service.removeUser(TOKEN);

    assertEquals("", cache.get(TOKEN));
  }

  @Test
  public void isUserOnCacheTrue() throws ExecutionException {
    cache.put(TOKEN, USERNAME);

    boolean result = service.checkExistence(TOKEN);

    assertTrue(result);
  }

  @Test
  public void isUserOnCacheFalse() throws ExecutionException {
    boolean result = service.checkExistence(TOKEN);

    assertFalse(result);
  }
}
