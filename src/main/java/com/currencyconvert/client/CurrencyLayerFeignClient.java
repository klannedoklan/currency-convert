package com.currencyconvert.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.currencyconvert.client.response.ConvertResponse;

@FeignClient(name = "currencylayer", url = "${currencyLayer.url}")
public interface CurrencyLayerFeignClient {

  @GetMapping("/convert")
  ConvertResponse convert(
      @RequestParam("from") String source,
      @RequestParam("to") String target,
      @RequestParam("amount") Double amount,
      @RequestParam("access_key") String accessKey);

}
