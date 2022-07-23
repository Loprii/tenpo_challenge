package com.tenpo.challenge.service;

import com.tenpo.challenge.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

  User signUp(User user);

  boolean checkExistence(String username);

  User findByUsername(String username);
}
