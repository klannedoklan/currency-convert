package com.currencyconvert.mapper;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;

import com.currencyconvert.client.response.ChangeResponse;
import com.currencyconvert.client.response.ConvertResponse;
import com.currencyconvert.data.ConversionHistoryEntity;
import com.currencyconvert.domain.dto.request.ConvertRateRequestDto;
import com.currencyconvert.domain.dto.response.ConvertResponseDto;
import com.currencyconvert.util.DateTime;
import com.example.currencyconvert.model.ConvertedCurrency;
import com.example.currencyconvert.model.CurrencyCode;
import com.example.currencyconvert.model.ExchangeRate;
import com.example.currencyconvert.model.PaginatedConversionHistory;
import com.example.currencyconvert.model.Pagination;

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
    long timestamp = conversionHistoryEntity.getTimestamp();
    convertedCurrency.setTimestamp(DateTime.toOffsetDateTime(timestamp * 1000));

    return convertedCurrency;
  }

  public static ConvertResponseDto toConvertResponseDto(ConvertResponse clientResponse) {
    CurrencyCode from = CurrencyCode.fromValue(clientResponse.getQuery().getFrom());
    CurrencyCode to = CurrencyCode.fromValue(clientResponse.getQuery().getTo());
    return new ConvertResponseDto(
        from,
        to,
        clientResponse.getQuery().getAmount(),
        clientResponse.getResult(),
        clientResponse.getInfo().getTimestamp()
    );
  }

  public static ExchangeRate toExchangeRate(ConvertRateRequestDto request, ChangeResponse changeResponse) {
    CurrencyCode source = request.source();
    CurrencyCode target = request.target();
    String targetSourceQuoteKey = String.format("%s%s", target, source);

    ExchangeRate exchangeRate = new ExchangeRate();
    exchangeRate.setSource(source);
    exchangeRate.setTarget(target);
    exchangeRate.setStartDate(changeResponse.getStartDate());
    exchangeRate.setEndDate(changeResponse.getEndDate());

    if (!changeResponse.getQuotes().containsKey(targetSourceQuoteKey)) {
      throw new NoSuchElementException("Quote for " + targetSourceQuoteKey + " not found in the response.");
    }

    ChangeResponse.Quote quote = changeResponse.getQuotes().get(targetSourceQuoteKey);
    exchangeRate.setStartRate(quote.getStartRate());
    exchangeRate.setEndRate(quote.getEndRate());
    exchangeRate.setChange(quote.getChange());
    exchangeRate.setChangePct(quote.getChangePct());

    return exchangeRate;
  }

  public static PaginatedConversionHistory toPaginatedConversionHistory(Page<ConversionHistoryEntity> historyPage) {
    Pagination pagination = PaginationMapper.toPagination(historyPage);
    PaginatedConversionHistory paginatedConversionHistory = new PaginatedConversionHistory();
    paginatedConversionHistory.setPagination(pagination);
    List<ConvertedCurrency> items = toConvertedCurrencies(historyPage.getContent());
    paginatedConversionHistory.setItems(items);

    return paginatedConversionHistory;
  }

  public static List<ConvertedCurrency> toConvertedCurrencies(List<ConversionHistoryEntity> historyEntities) {
    return historyEntities.stream()
        .map(CurrencyConvertMapper::toConvertedCurrencyResponse)
        .toList();
  }

}
