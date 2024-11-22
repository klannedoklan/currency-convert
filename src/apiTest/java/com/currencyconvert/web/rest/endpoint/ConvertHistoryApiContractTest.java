package com.currencyconvert.web.rest.endpoint;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import io.restassured.http.ContentType;

import org.skyscreamer.jsonassert.JSONAssert;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import org.json.JSONException;

import com.currencyconvert.data.ConversionHistoryEntity;
import com.currencyconvert.data.repository.JpaConversionHistoryRepository;
import com.currencyconvert.data.specification.ConversionHistorySpecification;
import com.currencyconvert.util.ResourceFile;
import com.currencyconvert.web.rest.BaseApiTest;
import com.example.currencyconvert.model.CurrencyCode;

public class ConvertHistoryApiContractTest extends BaseApiTest {

  private static final UUID TRANSACTION_ID = UUID.fromString("4ce492ef-46e9-4688-9252-b63c0aacbb4f");
  private static final long TIMESTAMP = 1732222396;
  private static final String CONVERT_HISTORY_URL = "/v1/convert/history";

  @MockBean
  private JpaConversionHistoryRepository jpaConversionHistoryRepository;

  @Test
  public void givenTransactionIdWhenGetHistoryThenReturnResponseStatusOk() throws JSONException {
    ConversionHistoryEntity conversionHistoryEntity = new ConversionHistoryEntity();
    conversionHistoryEntity.setTransactionId(TRANSACTION_ID);
    conversionHistoryEntity.setFrom(CurrencyCode.AED);
    conversionHistoryEntity.setTo(CurrencyCode.USD);
    conversionHistoryEntity.setAmount(350.0);
    conversionHistoryEntity.setResult(366.85705);
    conversionHistoryEntity.setTimestamp(TIMESTAMP);

    Page<ConversionHistoryEntity> conversionHistoryEntityPage = new PageImpl<>(Collections.singletonList(conversionHistoryEntity));

    when(jpaConversionHistoryRepository.findAll(any(ConversionHistorySpecification.class), any(Pageable.class)))
        .thenReturn(conversionHistoryEntityPage);

    final String responseBody =
        given()
            .mockMvc(mockMvc)
            .contentType(ContentType.JSON)
            .queryParams(Map.of(
                "transactionId", TRANSACTION_ID
            ))
        .when()
            .get(CONVERT_HISTORY_URL)
        .then()
            .contentType(ContentType.JSON)
            .status(HttpStatus.OK)
            .extract()
            .body()
            .asString();

    final String expectedResponseBody = ResourceFile.readJsonResourceToString("http/response/convert-history-response.json");
    JSONAssert.assertEquals(expectedResponseBody, responseBody, false);

  }

}
