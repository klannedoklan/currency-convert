package com.currencyconvert.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.currencyconvert.data.ConversionHistoryEntity;

@Repository
public interface JpaConversionHistoryRepository extends
    JpaRepository<ConversionHistoryEntity, Long>,
    JpaSpecificationExecutor<ConversionHistoryEntity> {

  Page<ConversionHistoryEntity> findAll(Specification<ConversionHistoryEntity> specification , Pageable pageable);

}
