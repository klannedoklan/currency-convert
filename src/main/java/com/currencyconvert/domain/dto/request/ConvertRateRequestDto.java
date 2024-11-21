package com.currencyconvert.domain.dto.request;

import com.example.currencyconvert.model.CurrencyCode;

public record ConvertRateRequestDto(
    CurrencyCode source,
    CurrencyCode target) {
}
