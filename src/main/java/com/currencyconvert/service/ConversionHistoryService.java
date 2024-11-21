package com.currencyconvert.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.currencyconvert.client.response.ConvertResponse;
import com.currencyconvert.data.ConversionHistoryEntity;
import com.currencyconvert.domain.dto.response.ConvertResponseDto;

public interface ConversionHistoryService {

  ConversionHistoryEntity persistHistory(ConvertResponseDto convertResponseDto);

  Page<ConversionHistoryEntity> getHistory(UUID transactionId, LocalDateTime dateTime, Pageable pageable);

}
