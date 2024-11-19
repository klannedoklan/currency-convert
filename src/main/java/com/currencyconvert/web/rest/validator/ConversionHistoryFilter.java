package com.currencyconvert.web.rest.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ConversionHistoryFilterValidator.class })
public @interface ConversionHistoryFilter {

  String message() default "must contain either of transactionId or createDate";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };

}
