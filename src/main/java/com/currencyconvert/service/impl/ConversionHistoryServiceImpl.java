package com.currencyconvert.service.impl;

import java.util.UUID;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.currencyconvert.data.ConversionHistoryEntity;
import com.currencyconvert.data.specification.ConversionHistorySpecification;
import com.currencyconvert.domain.dto.request.ConvertHistoryRequestDto;
import com.currencyconvert.domain.dto.response.ConvertResponseDto;
import com.currencyconvert.service.ConversionHistoryService;
import com.currencyconvert.data.repository.JpaConversionHistoryRepository;

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
  public Page<ConversionHistoryEntity> getHistory(ConvertHistoryRequestDto requestDto) {
    Pageable pageable = Pageable.ofSize(requestDto.size()).withPage(requestDto.page());
    Specification<ConversionHistoryEntity> specification = new ConversionHistorySpecification(requestDto);
    return jpaConversionHistoryRepository.findAll(specification, pageable);
  }

}
