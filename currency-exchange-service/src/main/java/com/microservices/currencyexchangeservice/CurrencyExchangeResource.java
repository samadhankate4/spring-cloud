package com.microservices.currencyexchangeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeResource {
    @Autowired
    private Environment environment;
    @Autowired
    private CurrencyExchangeRepository repository;

    @GetMapping("currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange currencyExchange(@PathVariable String from, @PathVariable String to) {

        /*CurrencyExchange currencyExchange = new CurrencyExchange(1000L, from, to, BigDecimal.valueOf(50));
        currencyExchange.setEnvironment(environment.getProperty("local.server.port"));
        return currencyExchange;*/
        CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);
        if(currencyExchange==null){
            throw new RuntimeException("result not found");
        }
        currencyExchange.setEnvironment(environment.getProperty("local.server.port"));
        return currencyExchange;
    }
}
