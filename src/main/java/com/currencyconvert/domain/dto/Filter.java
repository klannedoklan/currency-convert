package com.currencyconvert.domain.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface Filter {

  UUID getTransactionId();

  OffsetDateTime getCreatedDate();

}
