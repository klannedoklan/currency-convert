package com.currencyconvert.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.util.StringUtils;

@Slf4j
public final class DateTime {

  private static final long INFINITY_YEARS = 10000;
  private static final String ERROR_PARSING_TO_ISO_LOCAL_DATE = "Error parsing to ISO LocalDate: {}";
  private static final String ERROR_PARSING_TO_ISO_OFFSET_DATE_TIME = "Error parsing to ISO OffsetDateTime: {}";

  private DateTime() {
  }

  public static OffsetDateTime utcNow() {
    return OffsetDateTime.now(ZoneOffset.UTC);
  }

  public static Optional<OffsetDateTime> toOffsetDateTime(final String isoDateTime) {
    if (!StringUtils.hasText(isoDateTime)) {
      return Optional.empty();
    }

    try {
      final var offsetDateTime = OffsetDateTime.parse(isoDateTime);

      return Optional.of(offsetDateTime);
    } catch (Exception exception) {
      log.warn(ERROR_PARSING_TO_ISO_OFFSET_DATE_TIME, isoDateTime, exception);
    }

    return Optional.empty();
  }

  public static OffsetDateTime toOffsetDateTime(final long epochMilli) {
    return OffsetDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), ZoneOffset.UTC);
  }

  public static Optional<OffsetDateTime> toOffsetDateTimeIfNotInfinity(long epochMilli) {
    OffsetDateTime dateTime = toOffsetDateTime(epochMilli);
    OffsetDateTime now = utcNow();

    if (dateTime.isAfter(now.plusYears(INFINITY_YEARS))) {
      return Optional.empty();
    }

    return Optional.of(dateTime);
  }

  public static Long toEpochMilliseconds(final OffsetDateTime isoDateTimeUtc) {
    if (isoDateTimeUtc == null) {
      return null;
    }

    return isoDateTimeUtc.toInstant().toEpochMilli();
  }

  public static Optional<LocalDate> toLocalDate(final String isoDate) {
    if (!StringUtils.hasText(isoDate)) {
      return Optional.empty();
    }

    try {
      final var localDate = LocalDate.parse(isoDate);

      return Optional.of(localDate);
    } catch (Exception exception) {
      log.warn(ERROR_PARSING_TO_ISO_LOCAL_DATE, isoDate, exception);
    }

    return Optional.empty();
  }

  public static OffsetDateTime toEndOfDayUtcOffsetDateTime(final LocalDate localDate) {
    final var localTime = LocalTime.of(23, 59, 59);

    return OffsetDateTime.of(localDate, localTime, ZoneOffset.UTC);
  }

}
