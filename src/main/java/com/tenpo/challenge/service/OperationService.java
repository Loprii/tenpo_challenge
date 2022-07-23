package com.tenpo.challenge.service;

import com.tenpo.challenge.adapter.ResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface OperationService {

  ResponseDTO sum(double number1, double number2);
}
