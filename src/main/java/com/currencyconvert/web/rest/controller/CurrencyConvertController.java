package com.currencyconvert.web.rest.controller;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.example.currencyconvert.api.V1Api;
import com.example.currencyconvert.model.ConvertedCurrency;
import com.example.currencyconvert.model.CurrencyCode;
import com.example.currencyconvert.model.ExchangeRate;
import com.example.currencyconvert.model.PaginatedConversionHistory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class CurrencyConvertController implements V1Api {

  @Override
  public ResponseEntity<PaginatedConversionHistory> getConversionHistory(
      Integer page,
      Integer size,
      String transactionId,
      OffsetDateTime createdDate
  ) {
    return V1Api.super.getConversionHistory(page, size, transactionId, createdDate);
  }

  @Override
  public ResponseEntity<ConvertedCurrency> getConvertedCurrency(BigDecimal amount, CurrencyCode source, CurrencyCode target) {
    return V1Api.super.getConvertedCurrency(amount, source, target);
  }

  @Override
  public ResponseEntity<ExchangeRate> getExchangeRate(CurrencyCode source, CurrencyCode target) {
    return V1Api.super.getExchangeRate(source, target);
  }

}
