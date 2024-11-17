package com.currencyconvert.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.currencyconvert.data.ConversionHistoryEntity;

public interface ConversionHistoryService {

  Page<ConversionHistoryEntity> findAll(UUID transactionId, LocalDateTime dateTime, Pageable pageable);

}
