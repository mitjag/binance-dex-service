package com.binance.dex.service.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SigningService {
    
    private static final Logger log = LoggerFactory.getLogger(SigningService.class);
    
    @RequestMapping("/")
    public String index() {
        log.info("index requested");
        return "REST service";
    }
    
    @RequestMapping("/transfer")
    public String transfer(
            @RequestParam(name = "walletName") String walletName,
            @RequestParam(name = "walletPin") Integer pin,
            @RequestParam(name = "coin") String coin,
            @RequestParam(name = "toAddress") String toAddress,
            @RequestParam(name = "amount") Double amount) {
        log.info("Transfer");
        return "transfer";
    }
    
    @RequestMapping("/newOrder")
    public String newOrder() {
        log.info("New Order");
        return "newOrder";
    }
    
    @RequestMapping("/cancelOrder")
    public String cancelOrder() {
        log.info("Cancel Order");
        return "cancelOrder";
    }
}
