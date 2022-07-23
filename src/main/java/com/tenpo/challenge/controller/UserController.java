package com.tenpo.challenge.controller;

import com.tenpo.challenge.entity.User;
import com.tenpo.challenge.adapter.TokenDTO;
import com.tenpo.challenge.adapter.UserDTO;
import com.tenpo.challenge.exception.BadRequestException;
import com.tenpo.challenge.service.AuthService;
import com.tenpo.challenge.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.net.URI;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/v1/user")
@AllArgsConstructor
public class UserController {

  @Autowired private final UserService userService;
  @Autowired private final AuthService authService;

  @ApiOperation(value = "Registre su usuario")
  @PostMapping("/signup")
  public ResponseEntity<User> singUp(
      @ApiParam(value = "nuevo usuario", required = true) @Valid @RequestBody UserDTO user)
      throws BadRequestException {

    if (userService.checkExistence(user.getUsername())) {
      throw new BadRequestException();
    }

    URI uri =
        URI.create(
            ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/v1/user/signup")
                .toUriString());
    User newUser = User.builder().username(user.getUsername()).password(user.getPassword()).build();
    return ResponseEntity.created(uri).body(userService.signUp(newUser));
  }

  @ApiOperation(value = "Logearse con un usuario existente")
  @PostMapping("/login")
  public ResponseEntity<TokenDTO> login(
      @ApiParam(value = "credenciales", required = true) @Valid @RequestBody UserDTO credentials) {
    Authentication auth = authService.login(credentials.getUsername(), credentials.getPassword());
    return ResponseEntity.ok(authService.generateAccessToken(auth));
  }

  @ApiOperation(value = "Deslogearse")
  @GetMapping("/logout")
  public ResponseEntity<String> logout(
      @RequestHeader(required = true, value = "Authorization") String auth) {
    String username = authService.invalidateAccessToken(auth);
    return ResponseEntity.ok(String.format("El usuario %s ha cerrado sesion", username));
  }
}
