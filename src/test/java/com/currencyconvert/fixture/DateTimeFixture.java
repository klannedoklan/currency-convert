package com.currencyconvert.fixture;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

public final class DateTimeFixture {

  public static final OffsetDateTime ACTIVE_FROM = OffsetDateTime.of(2023, 3, 1, 11, 30, 20, 40, ZoneOffset.UTC);
  public static final OffsetDateTime ACTIVE_TO = OffsetDateTime.of(2123, 3, 1, 11, 30, 20, 40, ZoneOffset.UTC);

  public static final long ACTIVE_FROM_EPOCH_MILLISECONDS = ACTIVE_FROM.toInstant().toEpochMilli();
  public static final long ACTIVE_TO_EPOCH_MILLISECONDS = ACTIVE_TO.toInstant().toEpochMilli();

  private DateTimeFixture() {
  }

  public static Stream<Arguments> provideEpochMilliToOffsetDateTimeUtc() {
    return Stream.of(
        Arguments.of(-1L, OffsetDateTime.of(1969, 12, 31, 23, 59, 59, 999_000_000, ZoneOffset.UTC)),
        Arguments.of(0L, OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)),
        Arguments.of(1L, OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 1_000_000, ZoneOffset.UTC)),
        Arguments.of(1000L, OffsetDateTime.of(1970, 1, 1, 0, 0, 1, 0, ZoneOffset.UTC)),
        Arguments.of(1000000L, OffsetDateTime.of(1970, 1, 1, 0, 16, 40, 0, ZoneOffset.UTC)),
        Arguments.of(1000000000L, OffsetDateTime.of(1970, 1, 12, 13, 46, 40, 0, ZoneOffset.UTC)),
        Arguments.of(1000000000000L, OffsetDateTime.of(2001, 9, 9, 1, 46, 40, 0, ZoneOffset.UTC)),
        Arguments.of(1682931722000L, OffsetDateTime.of(2023, 5, 1, 9, 2, 2, 0, ZoneOffset.UTC)),
        Arguments.of(1685491319000L, OffsetDateTime.of(2023, 5, 31, 0, 1, 59, 0, ZoneOffset.UTC)),
        Arguments.of(1000000000000000L, OffsetDateTime.of(33658, 9, 27, 1, 46, 40, 0, ZoneOffset.UTC)),
        Arguments.of(9223372036854775807L, OffsetDateTime.of(292278994, 8, 17, 7, 12, 55, 807_000_000, ZoneOffset.UTC))
    );
  }

  public static Stream<Arguments> provideFiniteEpochMilliToOffsetDateTimeUtc() {
    return Stream.of(
        Arguments.of(-1L, OffsetDateTime.of(1969, 12, 31, 23, 59, 59, 999_000_000, ZoneOffset.UTC)),
        Arguments.of(0L, OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)),
        Arguments.of(1L, OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 1_000_000, ZoneOffset.UTC)),
        Arguments.of(1000L, OffsetDateTime.of(1970, 1, 1, 0, 0, 1, 0, ZoneOffset.UTC)),
        Arguments.of(1000000L, OffsetDateTime.of(1970, 1, 1, 0, 16, 40, 0, ZoneOffset.UTC)),
        Arguments.of(1000000000L, OffsetDateTime.of(1970, 1, 12, 13, 46, 40, 0, ZoneOffset.UTC)),
        Arguments.of(1000000000000L, OffsetDateTime.of(2001, 9, 9, 1, 46, 40, 0, ZoneOffset.UTC)),
        Arguments.of(1682931722000L, OffsetDateTime.of(2023, 5, 1, 9, 2, 2, 0, ZoneOffset.UTC)),
        Arguments.of(1685491319000L, OffsetDateTime.of(2023, 5, 31, 0, 1, 59, 0, ZoneOffset.UTC))
    );
  }

  public static Stream<Arguments> provideInfiniteEpochMilliToOffsetDateTimeUtc() {
    return Stream.of(
        Arguments.of(1000000000000000L),
        Arguments.of(9223372036854775807L)
    );
  }

  public static Stream<Arguments> provideRealDateValues() {
    return Stream.of(
        Arguments.of("2024-02-29"),
        Arguments.of("1900-12-10"),
        Arguments.of("1900-10-30"),
        Arguments.of("2999-01-01"),
        Arguments.of("2999-12-31")
    );
  }

  public static Stream<Arguments> provideRealDateValuesAndLocalDates() {
    return Stream.of(
        Arguments.of("2024-02-29", LocalDate.of(2024, 2, 29)),
        Arguments.of("1900-12-10", LocalDate.of(1900, 12, 10)),
        Arguments.of("1900-10-30", LocalDate.of(1900, 10, 30)),
        Arguments.of("2999-01-01", LocalDate.of(2999, 1, 1)),
        Arguments.of("2999-12-31", LocalDate.of(2999, 12, 31))
    );
  }

  public static Stream<Arguments> provideRealPastAndFutureDateValues() {
    return Stream.of(
        Arguments.of("2024-02-29", false),
        Arguments.of("1900-12-10", false),
        Arguments.of("1900-10-30", false),
        Arguments.of("2999-01-01", true),
        Arguments.of("2999-12-31", true)
    );
  }

  public static Stream<Arguments> provideNotRealDateValues() {
    return Stream.of(
        Arguments.of(""),
        Arguments.of(" "),
        Arguments.of("24-23-23"),
        Arguments.of("1900-13-10"),
        Arguments.of("1900-10-33"),
        Arguments.of("2024-02-30"),
        Arguments.of("9999-March-29th")
    );
  }

  public static Stream<Arguments> provideRealDateTimeValues() {
    return Stream.of(
        Arguments.of("1900-12-31T23:59:59Z"),
        Arguments.of("2024-02-29T12:12:12Z"),
        Arguments.of("2124-01-11T00:00:00Z"),
        Arguments.of("2999-01-01T23:59:59Z")
    );
  }

  public static Stream<Arguments> provideRealDateTimeValuesAndOffsetDateTimes() {
    return Stream.of(
        Arguments.of("1900-12-31T23:59:59Z", OffsetDateTime.of(1900, 12, 31, 23, 59, 59, 0, ZoneOffset.UTC)),
        Arguments.of("2024-02-29T12:12:12Z", OffsetDateTime.of(2024, 2, 29, 12, 12, 12, 0, ZoneOffset.UTC)),
        Arguments.of("2124-01-11T00:00:00Z", OffsetDateTime.of(2124, 1, 11, 0, 0, 0, 0, ZoneOffset.UTC)),
        Arguments.of("2999-01-01T23:59:59Z", OffsetDateTime.of(2999, 1, 1, 23, 59, 59, 0, ZoneOffset.UTC))
    );
  }

  public static Stream<Arguments> provideRealPastAndFutureDateTimeValues() {
    return Stream.of(
        Arguments.of("1900-12-31T23:59:59Z", false),
        Arguments.of("2024-02-29T12:12:12Z", false),
        Arguments.of("2524-01-11T00:00:00Z", true),
        Arguments.of("2999-01-01T23:59:59Z", true)
    );
  }

  public static Stream<Arguments> provideNotRealDateTimeValues() {
    return Stream.of(
        Arguments.of(""),
        Arguments.of(" "),
        Arguments.of("1900-12-32T23:59:59Z"),
        Arguments.of("1900-13-31T23:59:59Z"),
        Arguments.of("2024-02-30T12:12:12Z"),
        Arguments.of("2124-01-01T24:00:00Z"),
        Arguments.of("2124-01-01T23:60:00Z"),
        Arguments.of("2124-01-01T23:59:60Z")
    );
  }

}
