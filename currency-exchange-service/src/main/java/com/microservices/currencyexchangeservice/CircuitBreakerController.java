package com.microservices.currencyexchangeservice;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    private Logger logger= LoggerFactory.getLogger(CircuitBreakerController.class);
    @GetMapping("/sample-api")
//@Retry(name="sample-api", fallbackMethod = "HardCodedResponse")
  @CircuitBreaker(name = "default",fallbackMethod = "HardCodedResponse")
   @RateLimiter(name="default") // not of request per secand
    @Bulkhead(name="sample-api") // configure allowed concurrent call to api
    public String sampleApi(){
        logger.info("sample api invoked");
   /* ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8000/same-api", String.class);
    return forEntity.getBody();*/
        return "sample-api";
}
   public String HardCodedResponse(Exception e){
        return "HardCodedResponse";

    }
}
