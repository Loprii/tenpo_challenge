package com.tenpo.challenge.service.impl;

import com.tenpo.challenge.entity.History;
import com.tenpo.challenge.repository.HistoryRepository;
import com.tenpo.challenge.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tenpo.challenge.adapter.ResponseDTO;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

  @Autowired private final HistoryRepository historyRepository;

  public ResponseDTO sum(double number1, double number2) {
    return new ResponseDTO(number1 + number2);
  }
}
