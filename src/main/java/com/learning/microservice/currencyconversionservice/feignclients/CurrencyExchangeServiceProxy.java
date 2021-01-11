package com.learning.microservice.currencyconversionservice.feignclients;

import com.learning.microservice.currencyconversionservice.model.CurrencyConversionModel;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Muhammad Danish Khan
 * @created 6/1/21 - 11:48 PM
 */

@FeignClient(name = "currency-exchange-service")
@LoadBalancerClient("currency-exchange-service")
public interface CurrencyExchangeServiceProxy {
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversionModel retrieveExchangeValue(@PathVariable("from") String from, @PathVariable("to") String to);
}
