package com.currencyconvert.domain.dto;

import java.util.UUID;

public interface Filter {

  UUID getTransactionId();

  String getCreatedDate();

}
