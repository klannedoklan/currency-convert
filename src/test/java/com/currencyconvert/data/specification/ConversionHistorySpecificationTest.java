package com.currencyconvert.data.specification;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.UUID;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.currencyconvert.data.ConversionHistoryEntity;
import com.currencyconvert.domain.dto.request.ConvertHistoryRequestDto;

@ExtendWith(MockitoExtension.class)
public class ConversionHistorySpecificationTest {

  private static final String DATE_STRING = "2024-01-01";
  private static final LocalDate LOCAL_DATE = LocalDate.of(2024, 1, 1);
  private static final UUID UUID = java.util.UUID.fromString("0879b072-46ec-4893-a1b1-fa91889ff37e");
  private static final String TRANSACTION_ID = "transactionId";
  private static final String TIMESTAMP = "timestamp";

  @Mock
  private CriteriaBuilder criteriaBuilder;

  @Mock
  private CriteriaQuery<?> criteriaQuery;

  @Mock
  private Root<ConversionHistoryEntity> historyRoot;

  @Mock
  private Predicate transactionIdEqualPredicate;

  @Mock
  private Predicate timestampBetweenPredicate;

  @Mock
  private Predicate resultPredicate;

  @Mock
  private Path<UUID> transactionIdPath;

  @Mock
  private Path<Long> timestampPath;

  @InjectMocks
  private ConversionHistorySpecification conversionHistorySpecification;

  private ConvertHistoryRequestDto requestDto;

  @BeforeEach
  public void setUp() {
    requestDto = new ConvertHistoryRequestDto(0, 10, UUID, DATE_STRING);
    conversionHistorySpecification = new ConversionHistorySpecification(requestDto);
  }

  @Test
  public void givenAllListsConvertHistoryRequestWhenToPredicateThenVerifyResult() {
    // GIVEN
    mockAllPredicates();

    // WHEN
    Predicate predicate = conversionHistorySpecification.toPredicate(historyRoot, criteriaQuery, criteriaBuilder);

    // THEN
    assertEquals(resultPredicate, predicate);
  }

  private void mockAllPredicates() {
    when(historyRoot.<UUID>get(TRANSACTION_ID)).thenReturn(transactionIdPath);
    when(criteriaBuilder.equal(transactionIdPath, UUID)).thenReturn(transactionIdEqualPredicate);

    LocalDate localDate = LOCAL_DATE;
    long startDateTime = localDate.atStartOfDay().toEpochSecond(ZoneOffset.UTC);
    long endDateTime = localDate.atTime(23, 59, 59).toEpochSecond(ZoneOffset.UTC);
    when(historyRoot.<Long>get(TIMESTAMP)).thenReturn(timestampPath);
    when(criteriaBuilder.between(timestampPath, startDateTime, endDateTime)).thenReturn(timestampBetweenPredicate);

    Predicate[] predicates = new Predicate[] { transactionIdEqualPredicate, timestampBetweenPredicate };
    when(criteriaBuilder.and(predicates)).thenReturn(resultPredicate);
  }
}