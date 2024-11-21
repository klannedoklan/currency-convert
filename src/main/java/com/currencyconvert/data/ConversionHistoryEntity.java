package com.currencyconvert.data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import com.example.currencyconvert.model.CurrencyCode;

@Getter
@Setter
@Entity
@Table(name = "conversion_history")
public class ConversionHistoryEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private UUID transactionId;

  @Column(nullable = false)
  private Double amount;

  @Column(nullable = false)
  private Double result;

  @Column(nullable = false)
  @Enumerated(EnumType.ORDINAL)
  private CurrencyCode from;

  @Column(nullable = false)
  @Enumerated(EnumType.ORDINAL)
  private CurrencyCode to;

  @Column(nullable = false)
  private OffsetDateTime timestamp;

}
