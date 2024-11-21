package com.currencyconvert.web.rest.controller;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import javax.naming.ServiceUnavailableException;

import lombok.RequiredArgsConstructor;

import feign.FeignException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import io.vavr.control.Either;
import io.vavr.control.Try;

import com.currencyconvert.client.CurrencyLayerFeignClient;
import com.currencyconvert.client.response.ConvertResponse;
import com.currencyconvert.data.ConversionHistoryEntity;
import com.currencyconvert.domain.dto.request.ConvertHistoryRequestDto;
import com.currencyconvert.domain.dto.request.ConvertRequestDto;
import com.currencyconvert.domain.dto.request.ConvertRateRequestDto;
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
      OffsetDateTime createdDate
  ) {
    var request = new ConvertHistoryRequestDto(page, size, transactionId, createdDate);
    validateRequest(request);

    return V1Api.super.getConversionHistory(page, size, transactionId, createdDate);
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

    Either<Throwable, ConvertResponse> clientResult = Try.of(() -> currencyLayerFeignClient.convert(
            requestDto.source().getValue(),
            requestDto.target().getValue(),
            requestDto.amount().doubleValue(),
            requestDto.accessKey()
        ))
        .toEither()
        .mapLeft(this::handleClientError);

    ConvertResponse clientResponse = clientResult.get();

    ConvertResponseDto convertResponseDto = CurrencyConvertMapper.toConvertResponseDto(clientResponse);

    ConversionHistoryEntity conversionHistoryEntity = conversionHistoryService.persistHistory(convertResponseDto);

    ConvertedCurrency apiResponse = CurrencyConvertMapper.toConvertedCurrencyResponse(conversionHistoryEntity);

    return ResponseEntity.ok(apiResponse);
  }

  @Override
  public ResponseEntity<ExchangeRate> getExchangeRate(CurrencyCode from, CurrencyCode to, String accessKey) {
    var request = new ConvertRateRequestDto(from, to);
    validateRequest(request);

    return V1Api.super.getExchangeRate(from, to, accessKey);
  }

  private <T> void validateRequest(T request) {
    Set<ConstraintViolation<T>> constraintViolations = validator.validate(request);
    if (!constraintViolations.isEmpty()) {
      throw new ConstraintViolationException(constraintViolations);
    }
  }

  private Throwable handleClientError(Throwable throwable) {
    if (throwable instanceof FeignException.ServiceUnavailable) {
      return new ServiceUnavailableException();
    }
    return new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}