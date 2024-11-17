package com.currencyconvert.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.currencyconvert.service.ConversionHistoryService;
import com.currencyconvert.data.ConversionHistoryEntity;
import com.currencyconvert.repository.JpaConversionHistoryRepository;

@Service
@RequiredArgsConstructor
public class ConversionHistoryServiceImpl implements ConversionHistoryService {

  private final JpaConversionHistoryRepository jpaConversionHistoryRepository;

  @Override
  public Page<ConversionHistoryEntity> findAll(UUID transactionId, LocalDateTime dateTime, Pageable pageable) {
    return null;
  }

}
