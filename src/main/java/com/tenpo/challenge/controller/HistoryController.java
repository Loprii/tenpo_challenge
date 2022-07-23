package com.tenpo.challenge.controller;

import com.tenpo.challenge.entity.History;
import com.tenpo.challenge.service.HistoryService;
import java.util.List;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/history")
public class HistoryController {

  @Autowired private final HistoryService historyService;

  @GetMapping(value = "/getHistory")
  @ApiOperation(value = "Obtener el historial de operaciones. Primero debe autenticarse")
  public ResponseEntity<List<History>> getHistory(
      @RequestHeader(required = true, value = "Authorization") String auth)
 {
    List<History> tracesResponse = historyService.getHistory();
    return ResponseEntity.ok(tracesResponse);
  }
}
