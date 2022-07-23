package com.tenpo.challenge.service;

import com.tenpo.challenge.entity.History;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HistoryService {

  List<History> getHistory();
}
