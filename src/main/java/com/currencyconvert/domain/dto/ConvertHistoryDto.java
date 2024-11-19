package com.currencyconvert.domain.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.currencyconvert.web.rest.validator.ConversionHistoryFilter;

@ConversionHistoryFilter
public record ConvertHistoryDto(
    Integer size,
    Integer page,
    UUID transactionId,
    OffsetDateTime createdDate) implements Filter {

  @Override
  public UUID getTransactionId() {
    return transactionId;
  }

  @Override
  public OffsetDateTime getCreatedDate() {
    return createdDate;
  }

}
