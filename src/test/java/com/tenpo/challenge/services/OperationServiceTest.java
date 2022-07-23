package com.tenpo.challenge.services;

import com.tenpo.challenge.adapter.ResponseDTO;
import com.tenpo.challenge.repository.HistoryRepository;
import com.tenpo.challenge.service.OperationService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import com.tenpo.challenge.service.impl.AuthServiceImpl;
import com.tenpo.challenge.service.impl.OperationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationServiceTest {

  private OperationService service;
  private HistoryRepository historyRepository;

  @BeforeEach
  public void setUp() {
    historyRepository = mock(HistoryRepository.class);
    service = new OperationServiceImpl(historyRepository);
  }

  @Test
  public void sum() {
    ResponseDTO result = service.sum(32.0, 48.0);
    ResponseDTO expectedResult = new ResponseDTO(80.0);
    assertEquals(result, expectedResult);
  }
}
