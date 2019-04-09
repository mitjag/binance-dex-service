package com.binance.dex.service.rest;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.binance.dex.api.client.Wallet;
import com.binance.dex.api.client.domain.TransactionMetadata;
import com.binance.dex.api.client.domain.broadcast.CancelOrder;
import com.binance.dex.api.client.domain.broadcast.MultiTransfer;
import com.binance.dex.api.client.domain.broadcast.NewOrder;
import com.binance.dex.api.client.domain.broadcast.TokenFreeze;
import com.binance.dex.api.client.domain.broadcast.TokenUnfreeze;
import com.binance.dex.api.client.domain.broadcast.TransactionOption;
import com.binance.dex.api.client.domain.broadcast.Transfer;
import com.binance.dex.api.client.domain.broadcast.Vote;

@RestController
public class SigningService {
    
    private static final Logger log = LoggerFactory.getLogger(SigningService.class);
    
    @RequestMapping("/")
    public String index() {
        log.info("index requested");
        return "REST service";
    }

    @RequestMapping("/wallets")
    public List<String> wallets() {
        return new ArrayList<>();
    }
    
    @RequestMapping("/newOrder")
    public List<TransactionMetadata> newOrder(NewOrder newOrder, Wallet wallet, TransactionOption option) {
        log.info("New Order");
        return new ArrayList<>();
    }

    public List<TransactionMetadata> vote(Vote vote,Wallet wallet, TransactionOption options) {
        return new ArrayList<>();
    }
    

    @RequestMapping("/cancelOrder")
    List<TransactionMetadata> cancelOrder(CancelOrder cancelOrder, Wallet wallet, TransactionOption options) {
        log.info("Cancel Order");
        return new ArrayList<>();        
    }

    @RequestMapping("/transfer")
    String transfer(
            @RequestParam(name = "walletName", required = true) String walletName,
            @RequestParam(name = "walletPin", required = true) Integer pin,
            @RequestParam(name = "coin", required = true) String coin,
            @RequestParam(name = "toAddress", required = true) String toAddress,
            @RequestParam(name = "amount", required = true) Double amount) {
        //Transfer transfer, Wallet wallet, TransactionOption options
        log.info("Transfer");
        return "transfer";
    }
    
    @RequestMapping("/multiTransfer")
    public List<TransactionMetadata> multiTransfer(MultiTransfer multiTransfer, Wallet wallet, TransactionOption options) {
        return new ArrayList<>();
    }

    @RequestMapping("/freeze")
    public List<TransactionMetadata> freeze(TokenFreeze freeze, Wallet wallet, TransactionOption options) {
        return new ArrayList<>();
    }

    @RequestMapping("/unfreeze")
    public List<TransactionMetadata> unfreeze(TokenUnfreeze unfreeze, Wallet wallet, TransactionOption options) {
        return new ArrayList<>();
    }
    
    @RequestMapping("/broadcast")
    public List<TransactionMetadata> broadcast(String body) {
        return new ArrayList<>();
    }
}
