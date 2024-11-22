package com.currencyconvert.client;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.currencyconvert.client.response.ChangeResponse;
import com.currencyconvert.client.response.ConvertResponse;
import com.currencyconvert.configuration.CacheConfig;
import com.currencyconvert.configuration.FeignClientConfiguration;

@FeignClient(name = "currencylayer", url = "${currencyLayer.url}", configuration = FeignClientConfiguration.class)
public interface CurrencyLayerFeignClient {

  @GetMapping("/convert")
  ConvertResponse convert(
      @RequestParam("from") String source,
      @RequestParam("to") String target,
      @RequestParam("amount") Double amount,
      @RequestParam("access_key") String accessKey);

  @GetMapping("/change")
  @Cacheable(value = CacheConfig.CHANGE, key = "#currencies", unless = "#result == null")
  ChangeResponse getChange(
      @RequestParam("currencies") String currencies,
      @RequestParam("access_key") String accessKey);

}
