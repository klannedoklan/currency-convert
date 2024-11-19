package com.currencyconvert.web.rest.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { PositiveBigDecimalValidator.class })
public @interface PositiveBigDecimal {

  String message() default "must positive decimal number with up to {fractionalDigits} fractional digits";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };

  int fractionalDigits() default 3;

}
