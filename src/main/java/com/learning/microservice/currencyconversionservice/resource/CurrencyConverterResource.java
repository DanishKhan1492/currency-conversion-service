package com.learning.microservice.currencyconversionservice.resource;

import com.learning.microservice.currencyconversionservice.feignclients.CurrencyExchangeServiceProxy;
import com.learning.microservice.currencyconversionservice.model.CurrencyConversionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * @author Muhammad Danish Khan
 * @created 6/1/21 - 5:11 PM
 */
@RestController
public class CurrencyConverterResource {

    @Autowired
    private CurrencyExchangeServiceProxy currencyExchangeServiceProxy;

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionModel convertCurrency(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
        CurrencyConversionModel currencyConversionModel = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionModel.class, new HashMap<>() {{
            put("from", from);
            put("to", to);
        }}).getBody();
        currencyConversionModel.setQuantity(quantity);
        currencyConversionModel.setCalculatedAmount(currencyConversionModel.getQuantity().multiply(currencyConversionModel.getConversionMultiple()));
        return currencyConversionModel;
    }

    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionModel convertCurrencyUsingFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
        CurrencyConversionModel currencyConversionModel = currencyExchangeServiceProxy.retrieveExchangeValue(from, to);
        currencyConversionModel.setQuantity(quantity);
        currencyConversionModel.setCalculatedAmount(currencyConversionModel.getQuantity().multiply(currencyConversionModel.getConversionMultiple()));
        return currencyConversionModel;
    }

}
