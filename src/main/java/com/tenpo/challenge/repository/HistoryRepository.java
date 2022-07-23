package com.tenpo.challenge.repository;

import com.tenpo.challenge.entity.History;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends PagingAndSortingRepository<History, Long> {}
