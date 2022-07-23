package com.tenpo.challenge.services;

import com.tenpo.challenge.entity.History;
import com.tenpo.challenge.repository.HistoryRepository;
import com.tenpo.challenge.service.impl.HistoryServiceImpl;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class HistoryServiceTest {

  private HistoryServiceImpl service;
  private HistoryRepository repository;

  @BeforeEach
  public void setUp() {
    repository = mock(HistoryRepository.class);
    service = new HistoryServiceImpl(repository);
  }

  @Test
  void getTraces() {
    History history = new History("prueba", "200", LocalDateTime.now().toString());

    Page<History> traces = mock(Page.class);
    when(repository.findAll(any(Pageable.class))).thenReturn(traces);
    when(traces.hasContent()).thenReturn(true);
    when(traces.getContent()).thenReturn(List.of(history));

    List<History> results = service.getHistory();
    assertEquals(results.size(), 1);
    assertEquals(results.get(0), history);
  }

  @Test
  void getEmptyTrace() {
    Page<History> traces = mock(Page.class);
    when(repository.findAll(any(Pageable.class))).thenReturn(traces);
    when(traces.hasContent()).thenReturn(false);
    when(traces.getContent()).thenReturn(List.of());

    List<History> results = service.getHistory();
    assertEquals(results.size(), 0);
  }
}
