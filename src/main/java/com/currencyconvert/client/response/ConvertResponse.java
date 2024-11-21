package com.currencyconvert.client.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConvertResponse {

  private Query query;
  private Info info;
  private double result;

  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Query {
    private String from;
    private String to;
    private double amount;
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Info {
    private long timestamp;
    private double quote;
  }
}