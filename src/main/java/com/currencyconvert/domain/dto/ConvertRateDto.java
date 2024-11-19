// All Rights Reserved, Copyright © Paysafe Holdings UK Limited 2024. For more information see LICENSE

package com.currencyconvert.domain.dto;

import com.example.currencyconvert.model.CurrencyCode;

public record ConvertRateDto(
    CurrencyCode source,
    CurrencyCode target) {
}
