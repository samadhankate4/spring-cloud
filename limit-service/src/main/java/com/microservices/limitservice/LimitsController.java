package com.microservices.limitservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsController {
    @Autowired
    private Configuration configuration;
    @GetMapping("/limits")
    public Limit retriveLimits(){
        return new Limit(configuration.getMinimum(),configuration.getMaximum());
       // return new Limit(1,100);

    }
}
