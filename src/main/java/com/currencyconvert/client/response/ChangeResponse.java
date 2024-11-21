package com.currencyconvert.client.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import java.time.LocalDate;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangeResponse {
  @JsonProperty("start_date")
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate startDate;

  @JsonProperty("end_date")
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate endDate;

  private String source;

  private Map<String, Quote> quotes;

  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Quote {
    @JsonProperty("start_rate")
    private double startRate;

    @JsonProperty("end_rate")
    private double endRate;

    private double change;

    @JsonProperty("change_pct")
    private double changePct;
  }
}