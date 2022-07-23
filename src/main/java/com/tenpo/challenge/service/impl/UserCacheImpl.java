package com.tenpo.challenge.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.concurrent.TimeUnit;

import com.tenpo.challenge.service.UserCache;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class UserCacheImpl implements UserCache {

  private final LoadingCache<String, String> cache;

  public UserCacheImpl() {
    CacheLoader<String, String> loader;
    loader =
        new CacheLoader<String, String>() {
          @Override
          public String load(String key) {
            return "";
          }
        };

    cache =
        CacheBuilder.newBuilder()
            .maximumSize(500)
            .expireAfterWrite(60, TimeUnit.MINUTES)
            .build(loader);
  }

  public void removeUser(String token) {
    cache.invalidate(token);
  }

  public void addUser(String token, String username) {
    cache.put(token, username);
  }

  public boolean checkExistence(String token) {
    return (cache.getIfPresent(token) != null);
  }
}
