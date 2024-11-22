package com.currencyconvert.configuration;

import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class CacheConfig {

  public static final String CHANGE = "change";

  private final CacheProperties cacheProperties;

  @Bean
  public CacheManager cacheManager() {
    CaffeineCacheManager cacheManager = new CaffeineCacheManager(CHANGE);
    cacheManager.setCaffeine(Caffeine.newBuilder().expireAfterWrite(cacheProperties.getTtlSeconds(), TimeUnit.SECONDS));

    return cacheManager;
  }
}