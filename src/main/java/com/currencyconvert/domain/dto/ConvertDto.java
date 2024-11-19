package com.currencyconvert.domain.dto;

import java.math.BigDecimal;

import com.currencyconvert.web.rest.validator.PositiveBigDecimal;
import com.example.currencyconvert.model.CurrencyCode;

public record ConvertDto(
    @PositiveBigDecimal(fractionalDigits = 9)
    BigDecimal amount,
    CurrencyCode source,
    CurrencyCode target) {
}
