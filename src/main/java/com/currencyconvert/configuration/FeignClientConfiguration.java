package com.currencyconvert.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.currencyconvert.client.interceptor.RateLimitingInterceptor;

@Configuration
public class FeignClientConfiguration {
  private final RateLimitProperties properties;

  public FeignClientConfiguration(RateLimitProperties properties) {
    this.properties = properties;
  }

  @Bean
  public RateLimitingInterceptor rateLimitingInterceptor() {
    return new RateLimitingInterceptor(properties.getCapacity(), properties.getDays());
  }
}