package com.tenpo.challenge.controller;

import com.tenpo.challenge.adapter.ResponseDTO;
import com.tenpo.challenge.service.OperationService;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/operation")
public class OperationController {

  @Autowired private final OperationService OperationService;

  @GetMapping("/sum")
  @ApiOperation(value = "Sumar dos numeros. Primero debe autenticarse")
  public ResponseEntity<ResponseDTO> sum(
      @RequestHeader(required = true, value = "Authorization") String auth,
      @RequestParam(required = true) @NotEmpty double firstNumber,
      @RequestParam(required = true) @NotEmpty double secondNumber) {
    return ResponseEntity.ok(OperationService.sum(firstNumber, secondNumber));
  }
}
