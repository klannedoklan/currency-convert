package com.currencyconvert.domain.dto.request;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.currencyconvert.domain.dto.Filter;
import com.currencyconvert.web.rest.validator.ConversionHistoryFilter;

@ConversionHistoryFilter
public record ConvertHistoryRequestDto(
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
