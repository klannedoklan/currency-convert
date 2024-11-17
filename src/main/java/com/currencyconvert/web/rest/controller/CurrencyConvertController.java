package com.currencyconvert.web.rest.controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.currencyconvert.api.ConvertApi;
import com.example.currencyconvert.model.ConvertedCurrency;

@RestController
public class CurrencyConvertController implements ConvertApi {

  @Override
  public ResponseEntity<ConvertedCurrency> getConvertedCurrency(String sourceCurrencyCode, String targetCurrencyCode, BigDecimal amount) {
    return ConvertApi.super.getConvertedCurrency(sourceCurrencyCode, targetCurrencyCode, amount);
  }

}
