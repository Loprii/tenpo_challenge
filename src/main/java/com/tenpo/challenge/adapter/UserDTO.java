package com.tenpo.challenge.adapter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {

  private static final String NOT_USER_MESSAGE = "El usuario no puede ser vacio";

  private static final String NOT_PASSWORD_MESSAGE = "La clave no puede ser vacia";

  @NotNull(message = NOT_USER_MESSAGE)
  @NotEmpty(message = NOT_USER_MESSAGE)
  private String username;

  @NotNull(message = NOT_PASSWORD_MESSAGE)
  @NotEmpty(message = NOT_PASSWORD_MESSAGE)
  private String password;
}
