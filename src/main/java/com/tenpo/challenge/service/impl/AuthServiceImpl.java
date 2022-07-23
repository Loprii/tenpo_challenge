package com.tenpo.challenge.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tenpo.challenge.adapter.TokenDTO;
import static com.tenpo.challenge.filter.CustomAuthorizationFilter.BEARER;
import java.util.Date;
import java.util.stream.Collectors;

import com.tenpo.challenge.service.AuthService;
import com.tenpo.challenge.service.UserCache;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  public static final String SECRET_KEY = "jwt.secret.key";

  @Autowired private final AuthenticationManager authenticationManager;

  @Autowired private final UserCache cache;

  @Autowired private Environment env;

  public Authentication login(String username, String password) {
    try {
      return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password));
    } catch (BadCredentialsException e) {
      throw new BadCredentialsException("Credenciales Invalidas");
    }
  }

  public TokenDTO generateAccessToken(Authentication auth) {
    User user = (User) auth.getPrincipal();
    Algorithm algorithm = Algorithm.HMAC256(env.getProperty(SECRET_KEY).getBytes());
    Long startTime = System.currentTimeMillis();
    String access_token =
        JWT.create()
            .withSubject(user.getUsername())
            .withIssuedAt(new Date(startTime))
            .withExpiresAt(new Date(startTime +  15 * 60 * 1000))
            .withClaim(
                "roles",
                user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()))
            .sign(algorithm);
    cache.addUser(access_token, user.getUsername());
    return new TokenDTO(access_token);
  }

  public String invalidateAccessToken(String authHeader) {
    String token = authHeader.substring(BEARER.length());
    String username = getUsernameFromToken(token);
    if (cache.checkExistence(token)) {
      cache.removeUser(token);
    }
    return username;
  }

  private String getUsernameFromToken(String token) {
    Algorithm algorithm = Algorithm.HMAC256(env.getProperty(SECRET_KEY).getBytes());
    JWTVerifier verifier = JWT.require(algorithm).build();
    DecodedJWT decodedJWT = verifier.verify(token);
    return decodedJWT.getSubject();
  }
}
