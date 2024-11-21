package com.currencyconvert.mapper;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import com.currencyconvert.client.response.ConvertResponse;
import com.currencyconvert.data.ConversionHistoryEntity;
import com.currencyconvert.domain.dto.request.ConvertRequestDto;
import com.currencyconvert.domain.dto.response.ConvertResponseDto;
import com.example.currencyconvert.model.ConvertedCurrency;
import com.example.currencyconvert.model.CurrencyCode;

public final class CurrencyConvertMapper {

  private CurrencyConvertMapper() {
  }

  public static ConvertedCurrency toConvertedCurrencyResponse(ConversionHistoryEntity conversionHistoryEntity) {
    ConvertedCurrency convertedCurrency = new ConvertedCurrency();
    convertedCurrency.setTransactionId(conversionHistoryEntity.getTransactionId());
    convertedCurrency.setSource(conversionHistoryEntity.getFrom());
    convertedCurrency.setTarget(conversionHistoryEntity.getTo());
    convertedCurrency.setAmount(conversionHistoryEntity.getAmount());
    convertedCurrency.setResult(conversionHistoryEntity.getResult());
    convertedCurrency.setTimestamp(conversionHistoryEntity.getTimestamp());

    return convertedCurrency;
  }

  public static ConvertResponseDto toConvertResponseDto(ConvertResponse clientResponse) {
    CurrencyCode from = CurrencyCode.fromValue(clientResponse.getQuery().getFrom());
    CurrencyCode to = CurrencyCode.fromValue(clientResponse.getQuery().getTo());
    Instant timestamp = Instant.ofEpochMilli(clientResponse.getInfo().getTimestamp());
    OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(timestamp, ZoneOffset.UTC);
    return new ConvertResponseDto(
        from,
        to,
        clientResponse.getQuery().getAmount(),
        clientResponse.getResult(),
        offsetDateTime
    );
  }

}
