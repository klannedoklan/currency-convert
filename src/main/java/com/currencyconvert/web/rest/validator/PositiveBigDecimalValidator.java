package com.currencyconvert.web.rest.validator;

import java.math.BigDecimal;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PositiveBigDecimalValidator implements ConstraintValidator<PositiveBigDecimal, BigDecimal> {

  private int fractionalDigits;

  @Override
  public void initialize(PositiveBigDecimal constraintAnnotation) {
    fractionalDigits = constraintAnnotation.fractionalDigits();
  }

  @Override
  public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }

    return value.compareTo(BigDecimal.ZERO) > 0 && value.scale() <= fractionalDigits;
  }

}
