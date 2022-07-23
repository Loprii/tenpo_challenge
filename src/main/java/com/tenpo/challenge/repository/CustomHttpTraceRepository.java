package com.tenpo.challenge.repository;

import java.util.List;

import com.tenpo.challenge.entity.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "management.trace.http", name = "enabled", matchIfMissing = true)
public class CustomHttpTraceRepository implements HttpTraceRepository {

  @Autowired private HistoryRepository historyRepository;

  @Override
  public List<HttpTrace> findAll() {
    return null;
  }

  @Override
  public void add(HttpTrace httpTrace) {
    History history =
        History.builder()
            .description(httpTrace.getRequest().getUri().toString())
            .dateCreated(httpTrace.getTimestamp().toString())
            .Status(String.valueOf(httpTrace.getResponse().getStatus()))
            .build();
    historyRepository.save(history);
  }
}
