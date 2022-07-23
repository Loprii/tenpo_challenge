package com.tenpo.challenge.config;

import com.tenpo.challenge.filter.CustomAuthorizationFilter;
import com.tenpo.challenge.service.impl.UserCacheImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired private final UserDetailsService userDetailsService;
  @Autowired private final BCryptPasswordEncoder bCryptPasswordEncoder;
  @Autowired private final UserCacheImpl cache;
  @Autowired private final CustomAuthenticationEntryPoint customAuthenticatorEntryPoint;

  private static final String[] ENDPOINTS_AUTH = {
    "/v1/operation/sum/**", "/v1/history/history/**", "/v1/user/logout/**"
  };
  private static final String[] ENDPOINTS_WITHOUT_AUTH = {
    "/v2/api-docs",
    "/configuration/ui",
    "/swagger-resources/**",
    "/configuration/security",
    "/swagger-ui.html",
    "/webjars/**"
  };

  @Autowired private final Environment env;


  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers(ENDPOINTS_WITHOUT_AUTH);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .exceptionHandling()
        .authenticationEntryPoint(customAuthenticatorEntryPoint)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/v1/user/**")
        .permitAll()
        .antMatchers(ENDPOINTS_AUTH)
        .hasAnyAuthority("USER_ROLE")
        .anyRequest()
        .authenticated()
        .and()
        .addFilterBefore(
            new CustomAuthorizationFilter(env, cache), UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
