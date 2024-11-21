package com.currencyconvert.domain.dto.request;

import java.util.UUID;

import com.currencyconvert.domain.dto.Filter;
import com.currencyconvert.web.rest.validator.ConversionHistoryFilter;
import com.currencyconvert.web.rest.validator.RealDate;

@ConversionHistoryFilter
public record ConvertHistoryRequestDto(
    Integer page,
    Integer size,
    UUID transactionId,
    @RealDate
    String createdDate) implements Filter {

  @Override
  public UUID getTransactionId() {
    return transactionId;
  }

  @Override
  public String getCreatedDate() {
    return createdDate;
  }

}
