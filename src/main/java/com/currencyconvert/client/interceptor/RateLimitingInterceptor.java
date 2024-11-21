package com.currencyconvert.client.interceptor;

import java.time.Duration;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import org.springframework.stereotype.Component;

import com.currencyconvert.client.exception.RateLimitExceededException;
import com.currencyconvert.client.limiter.RateLimiter;

@Component
public class RateLimitingInterceptor implements RequestInterceptor {
  private final RateLimiter rateLimiter;

  public RateLimitingInterceptor(
      int capacity,
      int days) {
    this.rateLimiter = new RateLimiter(capacity, Duration.ofDays(days));
  }


  @Override
  public void apply(RequestTemplate template) {
    if (!rateLimiter.tryConsume()) {
      throw new RateLimitExceededException("Daily request limit exceeded");
    }
  }
}
