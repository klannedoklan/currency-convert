package com.currencyconvert.domain.dto.response;

import java.time.OffsetDateTime;

import com.example.currencyconvert.model.CurrencyCode;

public record ConvertResponseDto(
    CurrencyCode from,
    CurrencyCode to,
    Double amount,
    Double result,
    OffsetDateTime timestamp
) {

}
