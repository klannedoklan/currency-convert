package com.currencyconvert.web.rest.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.currencyconvert.domain.dto.Filter;

public class ConversionHistoryFilterValidator implements ConstraintValidator<ConversionHistoryFilter, Filter> {

  @Override
  public boolean isValid(Filter value, ConstraintValidatorContext context) {
    if (value.getTransactionId() != null || value.getCreatedDate() != null) {
      return true;
    }

    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate("must contain either of transactionId or createDate")
        .addPropertyNode("filter")
        .addConstraintViolation();

    return false;
  }

}
