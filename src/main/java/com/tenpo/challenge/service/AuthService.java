package com.tenpo.challenge.service;

import com.tenpo.challenge.adapter.TokenDTO;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthService {

  Authentication login(String username, String password);

  TokenDTO generateAccessToken(Authentication auth);

  String invalidateAccessToken(String authHeader);
}
