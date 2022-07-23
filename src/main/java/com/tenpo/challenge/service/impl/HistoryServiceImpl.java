package com.tenpo.challenge.service.impl;

import com.tenpo.challenge.entity.History;
import com.tenpo.challenge.repository.HistoryRepository;

import java.time.LocalDateTime;
import java.util.List;

import com.tenpo.challenge.service.HistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

  private static final Integer pageNumber = 0;
  private static final Integer pageSize = 10;
  private static final String sortBy = "id";

  @Autowired private final HistoryRepository historyRepository;

  public List<History> getHistory() {
    Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
    Page<History> history = historyRepository.findAll(paging);
    return history.hasContent() ? history.getContent() : List.of();
  }
}
