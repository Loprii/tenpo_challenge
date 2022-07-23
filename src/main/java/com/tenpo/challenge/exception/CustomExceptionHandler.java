package com.tenpo.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
    ErrorResponse error = new ErrorResponse("Ocurrio un error");
    return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(BadRequestException.class)
  public final ResponseEntity<Object> handleBadRequestExceptionException(
      BadRequestException ex, WebRequest request) {
    ErrorResponse error = new ErrorResponse("Los datos ingresados son erroneos");
    return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public final ResponseEntity<Object> handleBadCredentialsException(
      BadCredentialsException ex, WebRequest request) {
    ErrorResponse error = new ErrorResponse("Credenciales invalidas");
    return new ResponseEntity(error, HttpStatus.FORBIDDEN);
  }
}
