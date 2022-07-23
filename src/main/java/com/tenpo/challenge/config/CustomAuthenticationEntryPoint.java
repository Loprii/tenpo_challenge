package com.tenpo.challenge.config;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException {
    response.setContentType(APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response
        .getOutputStream()
        .println(
            "{ \"message\": \"Unauthorized request\","
                + "\"details\": [\"Primero debe loguearse para realizar esta operacion\"]}");
  }

  @Override
  public void afterPropertiesSet() {
    setRealmName("tenpo-entry-point");
    super.afterPropertiesSet();
  }
}
