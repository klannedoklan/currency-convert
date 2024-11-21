package com.currencyconvert.service;

import org.springframework.data.domain.Page;

import com.currencyconvert.data.ConversionHistoryEntity;
import com.currencyconvert.domain.dto.request.ConvertHistoryRequestDto;
import com.currencyconvert.domain.dto.response.ConvertResponseDto;

public interface ConversionHistoryService {

  ConversionHistoryEntity persistHistory(ConvertResponseDto convertResponseDto);

  Page<ConversionHistoryEntity> getHistory(ConvertHistoryRequestDto requestDto);

}
