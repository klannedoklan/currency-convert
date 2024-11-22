package com.currencyconvert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableCaching
@EnableConfigurationProperties
@SpringBootApplication
@EnableFeignClients(basePackages = "com.currencyconvert.client")
public class CurrencyConvertApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConvertApplication.class, args);
	}

}
