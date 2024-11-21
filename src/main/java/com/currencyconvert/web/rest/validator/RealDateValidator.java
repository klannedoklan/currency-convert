package com.currencyconvert.web.rest.validator;

import java.time.LocalDate;
import java.util.Optional;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import lombok.extern.slf4j.Slf4j;

import com.currencyconvert.util.DateTime;

@Slf4j
public class RealDateValidator implements ConstraintValidator<RealDate, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }

    final Optional<LocalDate> localDateOptional = DateTime.toLocalDate(value);

    return localDateOptional.isPresent();
  }

}
