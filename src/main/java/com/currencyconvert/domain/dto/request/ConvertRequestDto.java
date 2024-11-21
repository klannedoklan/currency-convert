package com.currencyconvert.domain.dto.request;

import java.math.BigDecimal;

import com.currencyconvert.web.rest.validator.PositiveBigDecimal;
import com.example.currencyconvert.model.CurrencyCode;

public record ConvertRequestDto(
    CurrencyCode source,
    CurrencyCode target,
    @PositiveBigDecimal(fractionalDigits = 9)
    BigDecimal amount,
    String accessKey) {
}
