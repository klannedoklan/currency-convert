package com.currencyconvert.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.currencyconvert.data.ConversionHistoryEntity;

@Repository
public interface JpaConversionHistoryRepository extends
    JpaRepository<ConversionHistoryEntity, Long>,
    JpaSpecificationExecutor<ConversionHistoryEntity> {
}
