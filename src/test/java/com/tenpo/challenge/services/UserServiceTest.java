package com.tenpo.challenge.services;


import com.tenpo.challenge.entity.User;
import com.tenpo.challenge.repository.HistoryRepository;
import com.tenpo.challenge.repository.UserRepository;
import com.tenpo.challenge.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTest {

  private static final String USERNAME = "user";
  private static final String PASSWORD = "pass";

  private UserServiceImpl service;
  private UserRepository repository;
  private PasswordEncoder encoder;

  @BeforeEach
  public void setUp() {
    repository = mock(UserRepository.class);
    encoder = mock(PasswordEncoder.class);
    service = new UserServiceImpl(repository, encoder);
  }

  @Test
  void userFound() {
    User user = new User(USERNAME, PASSWORD);

    when(repository.findByUsername(USERNAME)).thenReturn(user);

    UserDetails result = service.loadUserByUsername(USERNAME);
    assertEquals(result.getUsername(), USERNAME);
    assertEquals(result.getPassword(), PASSWORD);
  }

  @Test
  void userNotFound() {
    User user = new User(USERNAME, PASSWORD);

    when(repository.findByUsername(USERNAME)).thenReturn(null);

    Exception exception =
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(USERNAME));

    Assertions.assertTrue(exception instanceof UsernameNotFoundException);
    assertEquals(exception.getMessage(), "Usuario no encontrado");
  }

  @Test
  void registerUser() {
    User userToSave = new User(USERNAME, PASSWORD);

    when(encoder.encode(PASSWORD)).thenReturn(PASSWORD);
    when(repository.save(userToSave)).thenReturn(userToSave);

    User user = service.signUp(userToSave);
    assertEquals(user.getUsername(), userToSave.getUsername());
  }

  @Test
  void isUserRegisterTrue() {
    User userFound = new User(USERNAME, PASSWORD);

    when(repository.findByUsername(USERNAME)).thenReturn(userFound);

    boolean result = service.checkExistence(USERNAME);

    assertTrue(result);
  }

  @Test
  void isUserRegisterFalse() {
    when(repository.findByUsername(USERNAME)).thenReturn(null);

    boolean result = service.checkExistence(USERNAME);

    assertFalse(result);
  }
}
