package com.currencyconvert.client.limiter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

import java.time.Duration;

public class RateLimiter {
  private final Bucket bucket;

  public RateLimiter(int capacity, Duration refillDuration) {
    Bandwidth limit = Bandwidth.classic(capacity, Refill.intervally(capacity, refillDuration));
    this.bucket = Bucket.builder().addLimit(limit).build();
  }

  public boolean tryConsume() {
    return bucket.tryConsume(1);
  }
}
