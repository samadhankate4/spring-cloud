package com.microservices.currencyconversionservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionResource {





   @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
public CurrencyConversion currencyConversion(@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal quantity){
        RestTemplate restTemplate = new RestTemplate();
        HashMap<String, String> uriVariable = new HashMap<>();
        uriVariable.put("from",from);
        uriVariable.put("to",to);
        //http://localhost:8000/currency-exchange/from/USD/to/INR
        ResponseEntity<CurrencyConversion> forEntity = restTemplate.getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, uriVariable);
        CurrencyConversion currencyConversion = forEntity.getBody();
        currencyConversion.setQuantity(quantity);
        currencyConversion.setTotalCalculatedAmount(currencyConversion.getQuantity().multiply(currencyConversion.getConversionMultiple()));
        return currencyConversion;
        //return new CurrencyConversion(200L,from,to,quantity, BigDecimal.ONE,BigDecimal.TEN,"");
}

@Autowired
   private CurrencyExchangeProxy proxy;
   @GetMapping("/currency-conversion-fein/from/{from}/to/{to}/quantity/{quantity}")
   public CurrencyConversion currencyConversionFein(@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal quantity){
       CurrencyConversion currencyConversion = proxy.currencyExchange(from, to);
       currencyConversion.setQuantity(quantity);
       currencyConversion.setTotalCalculatedAmount(currencyConversion.getQuantity().multiply(currencyConversion.getConversionMultiple()));
       return currencyConversion;
   }

}
