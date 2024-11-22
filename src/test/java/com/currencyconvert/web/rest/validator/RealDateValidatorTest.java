package com.currencyconvert.web.rest.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.validation.ConstraintValidatorContext;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RealDateValidatorTest {

  @Mock
  private ConstraintValidatorContext context;

  @InjectMocks
  private RealDateValidator realDateValidator;

  @Test
  void givenNullDateWhenIsValidThenReturnTrue() {
    // GIVEN & WHEN
    final boolean isValid = realDateValidator.isValid(null, context);

    // THEN
    assertTrue(isValid);
  }

  @ParameterizedTest
  @MethodSource("com.currencyconvert.fixture.DateTimeFixture#provideRealDateValues")
  void givenRealDateValuesWhenIsValidThenReturnTrue(String value) {
    // GIVEN & WHEN
    final boolean isValid = realDateValidator.isValid(value, context);

    // THEN
    assertTrue(isValid);
  }

  @ParameterizedTest
  @MethodSource("com.currencyconvert.fixture.DateTimeFixture#provideNotRealDateValues")
  void givenNotRealDateValuesWhenIsValidThenReturnFalse(String value) {
    // GIVEN & WHEN
    final boolean isValid = realDateValidator.isValid(value, context);

    // THEN
    assertFalse(isValid);
  }

}
