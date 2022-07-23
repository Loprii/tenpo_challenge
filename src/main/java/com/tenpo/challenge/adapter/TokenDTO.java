package com.tenpo.challenge.adapter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TokenDTO implements Serializable {

  private static final long serialVersionUID = 1L;
  private String JWT;
}
