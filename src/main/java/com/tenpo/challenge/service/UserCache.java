package com.tenpo.challenge.service;

import org.springframework.stereotype.Service;

@Service
public interface UserCache {

  void removeUser(String token);

  void addUser(String token, String username);

  boolean checkExistence(String token);
}
