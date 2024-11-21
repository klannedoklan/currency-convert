package com.currencyconvert.data.specification;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import lombok.RequiredArgsConstructor;

import org.springframework.data.jpa.domain.Specification;

import com.currencyconvert.data.ConversionHistoryEntity;
import com.currencyconvert.domain.dto.request.ConvertHistoryRequestDto;
import com.currencyconvert.util.DateTime;

@RequiredArgsConstructor
public class ConversionHistorySpecification implements Specification<ConversionHistoryEntity> {

  private final ConvertHistoryRequestDto requestDto;

  @Override
  public Predicate toPredicate(Root<ConversionHistoryEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    List<Predicate> predicates = new ArrayList<>();

    if (requestDto.transactionId() != null) {
      predicates.add(cb.equal(root.get("transactionId"), requestDto.transactionId()));
    }

    Optional<LocalDate> optionalLocalDate = DateTime.toLocalDate(requestDto.createdDate());
    optionalLocalDate.ifPresent(localDate -> {
      long startDateTime = localDate.atStartOfDay().toEpochSecond(ZoneOffset.UTC);
      long endDateTime = localDate.atTime(23, 59, 59).toEpochSecond(ZoneOffset.UTC);
      Predicate datePredicate = cb.between(root.get("timestamp"), startDateTime, endDateTime);
      predicates.add(datePredicate);
    });

    return cb.and(predicates.toArray(new Predicate[0]));
  }
}