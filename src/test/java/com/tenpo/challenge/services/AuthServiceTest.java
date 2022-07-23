package com.tenpo.challenge.services;

import com.tenpo.challenge.service.AuthService;
import com.tenpo.challenge.service.UserCache;
import com.tenpo.challenge.service.impl.AuthServiceImpl;
import com.tenpo.challenge.service.impl.UserCacheImpl;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

public class AuthServiceTest {

  private static final String USERNAME = "user";
  private static final String PASSWORD = "admin";

  private AuthenticationManager authenticationManager;
  private UserCache cache;
  private AuthService service;

  @BeforeEach
  public void setUp() {
    cache = mock(UserCacheImpl.class);
    authenticationManager = mock(AuthenticationManager.class);
    service = new AuthServiceImpl(authenticationManager, cache);
  }

  @Test
  void WhenAuthenticationUserThenOk() {
    Authentication auth = mock(Authentication.class);
    when(auth.isAuthenticated()).thenReturn(Boolean.TRUE);

    when(authenticationManager.authenticate(any())).thenReturn(auth);

    Authentication authentication = service.login(USERNAME, PASSWORD);
    assertTrue(authentication.isAuthenticated());
  }

  @Test
  void WhenAuthenticationThenThrowUserBadCredentials() {
    when(authenticationManager.authenticate(any())).thenThrow(BadCredentialsException.class);

    Exception exception =
        assertThrows(BadCredentialsException.class, () -> service.login(USERNAME, PASSWORD));

    Assertions.assertTrue(exception instanceof BadCredentialsException);
    assertEquals(exception.getMessage(), "Credenciales Invalidas");
  }

}
