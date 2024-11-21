package com.currencyconvert.web.rest.controller;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import lombok.RequiredArgsConstructor;

import feign.FeignException;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import io.vavr.control.Either;
import io.vavr.control.Try;

import com.currencyconvert.client.CurrencyLayerFeignClient;
import com.currencyconvert.client.exception.RateLimitExceededException;
import com.currencyconvert.client.response.ChangeResponse;
import com.currencyconvert.client.response.ConvertResponse;
import com.currencyconvert.data.ConversionHistoryEntity;
import com.currencyconvert.domain.dto.request.ConvertHistoryRequestDto;
import com.currencyconvert.domain.dto.request.ConvertRateRequestDto;
import com.currencyconvert.domain.dto.request.ConvertRequestDto;
import com.currencyconvert.domain.dto.response.ConvertResponseDto;
import com.currencyconvert.mapper.CurrencyConvertMapper;
import com.currencyconvert.service.ConversionHistoryService;
import com.example.currencyconvert.api.V1Api;
import com.example.currencyconvert.model.ConvertedCurrency;
import com.example.currencyconvert.model.CurrencyCode;
import com.example.currencyconvert.model.ExchangeRate;
import com.example.currencyconvert.model.PaginatedConversionHistory;

@RestController
@RequiredArgsConstructor
public class CurrencyConvertController implements V1Api {

  private final Validator validator;
  private final CurrencyLayerFeignClient currencyLayerFeignClient;
  private final ConversionHistoryService conversionHistoryService;

  @Override
  public ResponseEntity<PaginatedConversionHistory> getConversionHistory(
      Integer page,
      Integer size,
      UUID transactionId,
      String createdDate
  ) {
    var request = new ConvertHistoryRequestDto(page, size, transactionId, createdDate);
    validateRequest(request);

    Page<ConversionHistoryEntity> historyPage = conversionHistoryService.getHistory(request);
    PaginatedConversionHistory apiResponse = CurrencyConvertMapper.toPaginatedConversionHistory(historyPage);

    return ResponseEntity.ok(apiResponse);
  }

  @Override
  public ResponseEntity<ConvertedCurrency> getConvertedCurrency(
      BigDecimal amount,
      CurrencyCode from,
      CurrencyCode to,
      String accessKey
  ) {
    var requestDto = new ConvertRequestDto(from, to, amount, accessKey);
    validateRequest(requestDto);

    Either<Throwable, ConvertResponse> clientResult = Try.of(() ->
            currencyLayerFeignClient.convert(
                requestDto.source().getValue(),
                requestDto.target().getValue(),
                requestDto.amount().doubleValue(),
                requestDto.accessKey()
            )
        )
        .toEither();

    if (clientResult.isLeft()) {
      throwException(clientResult.getLeft());
    }

    ConvertResponseDto convertResponseDto = CurrencyConvertMapper.toConvertResponseDto(clientResult.get());
    ConversionHistoryEntity conversionHistoryEntity = conversionHistoryService.persistHistory(convertResponseDto);
    ConvertedCurrency apiResponse = CurrencyConvertMapper.toConvertedCurrencyResponse(conversionHistoryEntity);

    return ResponseEntity.ok(apiResponse);
  }

  @Override
  public ResponseEntity<ExchangeRate> getExchangeRate(CurrencyCode from, CurrencyCode to, String accessKey) {
    var request = new ConvertRateRequestDto(from, to);
    validateRequest(request);

    Either<Throwable, ChangeResponse> clientResponse = Try.of(() ->
            currencyLayerFeignClient.getChange(
                String.format("%s,%s", request.source().getValue(), request.target().getValue()),
                accessKey)
        )
        .toEither();

    if (clientResponse.isLeft()) {
      throwException(clientResponse.getLeft());
    }

    ExchangeRate apiResponse = CurrencyConvertMapper.toExchangeRate(request, clientResponse.get());

    return ResponseEntity.ok(apiResponse);
  }

  private <T> void validateRequest(T request) {
    Set<ConstraintViolation<T>> constraintViolations = validator.validate(request);
    if (!constraintViolations.isEmpty()) {
      throw new ConstraintViolationException(constraintViolations);
    }
  }

  private <T> T throwException(Throwable throwable) {
    if (throwable instanceof FeignException.ServiceUnavailable) {
      throw new HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE, "Conversion API unavailable.");
    } else if (throwable instanceof RateLimitExceededException) {
      throw new HttpClientErrorException(HttpStatus.TOO_MANY_REQUESTS, "Reached maximum amount of daily requests.");
    }
    throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error.");
  }
}