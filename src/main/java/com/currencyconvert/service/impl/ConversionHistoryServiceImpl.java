package com.currencyconvert.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.currencyconvert.client.response.ConvertResponse;
import com.currencyconvert.data.ConversionHistoryEntity;
import com.currencyconvert.domain.dto.response.ConvertResponseDto;
import com.currencyconvert.service.ConversionHistoryService;
import com.currencyconvert.repository.JpaConversionHistoryRepository;

@Service
@RequiredArgsConstructor
public class ConversionHistoryServiceImpl implements ConversionHistoryService {

  private final JpaConversionHistoryRepository jpaConversionHistoryRepository;

  @Override
  public ConversionHistoryEntity persistHistory(ConvertResponseDto convertResponseDto) {
    UUID transactionId = UUID.randomUUID();
    ConversionHistoryEntity conversionHistoryEntity = new ConversionHistoryEntity();

    conversionHistoryEntity.setTransactionId(transactionId);
    conversionHistoryEntity.setFrom(convertResponseDto.from());
    conversionHistoryEntity.setTo(convertResponseDto.to());
    conversionHistoryEntity.setAmount(convertResponseDto.amount());
    conversionHistoryEntity.setResult(convertResponseDto.result());
    conversionHistoryEntity.setTimestamp(convertResponseDto.timestamp());

    return jpaConversionHistoryRepository.save(conversionHistoryEntity);
  }

  @Override
  public Page<ConversionHistoryEntity> getHistory(UUID transactionId, LocalDateTime dateTime, Pageable pageable) {
    return null;
  }

}
