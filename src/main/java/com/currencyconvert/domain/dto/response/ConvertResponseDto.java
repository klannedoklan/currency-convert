package com.currencyconvert.domain.dto.response;

import com.example.currencyconvert.model.CurrencyCode;

public record ConvertResponseDto(
    CurrencyCode from,
    CurrencyCode to,
    Double amount,
    Double result,
    Long timestamp
) {

}
