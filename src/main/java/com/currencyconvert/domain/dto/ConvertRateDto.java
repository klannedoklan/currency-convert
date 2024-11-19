package com.currencyconvert.domain.dto;

import com.example.currencyconvert.model.CurrencyCode;

public record ConvertRateDto(
    CurrencyCode source,
    CurrencyCode target) {
}
