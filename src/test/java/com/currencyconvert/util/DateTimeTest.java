package com.currencyconvert.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.currencyconvert.fixture.DateTimeFixture;

public class DateTimeTest {

  @ParameterizedTest
  @MethodSource("com.currencyconvert.fixture.DateTimeFixture#provideRealDateTimeValuesAndOffsetDateTimes")
  public void givenRealDateTimeValuesAndOffsetDateTimesWhenToOffsetDateTimeThenVerifyResult(
      String isoDateTime,
      OffsetDateTime expectedOffsetDateTime
  ) {
    // GIVEN & WHEN
    final Optional<OffsetDateTime> result = DateTime.toOffsetDateTime(isoDateTime);

    // THEN
    assertTrue(result.isPresent());
    assertEquals(expectedOffsetDateTime, result.get());
  }

  @ParameterizedTest
  @MethodSource("com.currencyconvert.fixture.DateTimeFixture#provideNotRealDateTimeValues")
  public void givenNotRealDateTimeValuesWhenToOffsetDateThenVerifyResultEmpty(String isoDateTime) {
    // GIVEN & WHEN
    final Optional<OffsetDateTime> result = DateTime.toOffsetDateTime(isoDateTime);

    // THEN
    assertTrue(result.isEmpty());
  }

  @ParameterizedTest
  @MethodSource("com.currencyconvert.fixture.DateTimeFixture#provideEpochMilliToOffsetDateTimeUtc")
  public void givenEpochMilliWhenToOffsetDateTimeThenVerifyResult(long epochMilli, OffsetDateTime expectedOffsetDateTime) {
    // GIVEN & WHEN
    final OffsetDateTime offsetDateTime = DateTime.toOffsetDateTime(epochMilli);

    // THEN
    assertEquals(expectedOffsetDateTime, offsetDateTime);
  }

  @ParameterizedTest
  @MethodSource("com.currencyconvert.fixture.DateTimeFixture#provideFiniteEpochMilliToOffsetDateTimeUtc")
  public void givenFiniteEpochMilliWhenToOffsetDateTimeIfNotInfinityThenVerifyResult(long epochMilli, OffsetDateTime expectedOffsetDateTime) {
    // GIVEN & WHEN
    final Optional<OffsetDateTime> offsetDateTimeOptional = DateTime.toOffsetDateTimeIfNotInfinity(epochMilli);

    // THEN
    assertTrue(offsetDateTimeOptional.isPresent());
    assertEquals(expectedOffsetDateTime, offsetDateTimeOptional.get());
  }

  @ParameterizedTest
  @MethodSource("com.currencyconvert.fixture.DateTimeFixture#provideInfiniteEpochMilliToOffsetDateTimeUtc")
  public void givenInfiniteEpochMilliWhenToOffsetDateTimeIfNotInfinityThenVerifyResultEmpty(long epochMilli) {
    // GIVEN & WHEN
    final Optional<OffsetDateTime> offsetDateTimeOptional = DateTime.toOffsetDateTimeIfNotInfinity(epochMilli);

    // THEN
    assertTrue(offsetDateTimeOptional.isEmpty());
  }

  @ParameterizedTest
  @MethodSource("com.currencyconvert.fixture.DateTimeFixture#provideRealDateValuesAndLocalDates")
  public void givenRealDateValuesAndLocalDatesWhenToLocalDateThenVerifyResult(String isoDate, LocalDate expectedLocalDate) {
    // GIVEN & WHEN
    final Optional<LocalDate> result = DateTime.toLocalDate(isoDate);

    // THEN
    assertTrue(result.isPresent());
    assertEquals(expectedLocalDate, result.get());
  }

  @ParameterizedTest
  @MethodSource("com.currencyconvert.fixture.DateTimeFixture#provideNotRealDateValues")
  public void givenNotRealDateValuesWhenToLocalDateThenVerifyResultEmpty(String isoDate) {
    // GIVEN & WHEN
    final Optional<LocalDate> result = DateTime.toLocalDate(isoDate);

    // THEN
    assertTrue(result.isEmpty());
  }

  @Test
  public void givenNullOffsetDateTimeWhenToEpochMilliThenVerifyResultNull() {
    // GIVEN & WHEN
    final Long result = DateTime.toEpochMilliseconds(null);

    // THEN
    assertNull(result);
  }

  @Test
  public void givenOffsetDateTimeWhenToEpochMilliThenVerifyResult() {
    // GIVEN & WHEN
    final Long result = DateTime.toEpochMilliseconds(DateTimeFixture.ACTIVE_FROM);

    // THEN
    assertEquals(DateTimeFixture.ACTIVE_FROM_EPOCH_MILLISECONDS, result);
  }

  @Test
  public void givenLocalDateWhenToEndOfDayUtcOffsetDateTimeThenVerifyResult() {
    // GIVEN
    final var localDate = LocalDate.of(2024, 2, 29);

    // WHEN
    final OffsetDateTime offsetDateTime = DateTime.toEndOfDayUtcOffsetDateTime(localDate);

    // THEN
    assertEquals(localDate.getYear(), offsetDateTime.getYear());
    assertEquals(localDate.getMonthValue(), offsetDateTime.getMonthValue());
    assertEquals(localDate.getDayOfMonth(), offsetDateTime.getDayOfMonth());
    assertEquals(23, offsetDateTime.getHour());
    assertEquals(59, offsetDateTime.getMinute());
    assertEquals(59, offsetDateTime.getSecond());
    assertEquals(ZoneOffset.UTC, offsetDateTime.getOffset());
  }

}
