package com.currencyconvert.web.rest.controller;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.currencyconvert.domain.dto.ConvertHistoryDto;
import com.currencyconvert.domain.dto.ConvertDto;
import com.currencyconvert.domain.dto.ConvertRateDto;
import com.example.currencyconvert.api.V1Api;
import com.example.currencyconvert.model.ConvertedCurrency;
import com.example.currencyconvert.model.CurrencyCode;
import com.example.currencyconvert.model.ExchangeRate;
import com.example.currencyconvert.model.PaginatedConversionHistory;

@RestController
@RequiredArgsConstructor
public class CurrencyConvertController implements V1Api {

  private final Validator validator;

  @Override
  public ResponseEntity<PaginatedConversionHistory> getConversionHistory(
      Integer page,
      Integer size,
      UUID transactionId,
      OffsetDateTime createdDate
  ) {
    var request = new ConvertHistoryDto(page, size, transactionId, createdDate);
    validateRequest(request);

    return V1Api.super.getConversionHistory(page, size, transactionId, createdDate);
  }

  @Override
  public ResponseEntity<ConvertedCurrency> getConvertedCurrency(BigDecimal amount, CurrencyCode source, CurrencyCode target) {
    var request = new ConvertDto(amount, source, target);
    validateRequest(request);

    return V1Api.super.getConvertedCurrency(amount, source, target);
  }

  @Override
  public ResponseEntity<ExchangeRate> getExchangeRate(CurrencyCode source, CurrencyCode target) {
    var request = new ConvertRateDto(source, target);
    validateRequest(request);

    return V1Api.super.getExchangeRate(source, target);
  }

  private <T> void validateRequest(T request) {
    Set<ConstraintViolation<T>> constraintViolations = validator.validate(request);
    if (!constraintViolations.isEmpty()) {
      throw new ConstraintViolationException(constraintViolations);
    }
  }
}