package com.binance.dex.service.rest;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

import com.binance.dex.api.client.BinanceDexApiClientFactory;
import com.binance.dex.api.client.BinanceDexApiRestClient;
import com.binance.dex.api.client.Wallet;
import com.binance.dex.api.client.domain.broadcast.TransactionOption;
import com.binance.dex.api.client.domain.broadcast.Transfer;
import com.binance.dex.api.client.encoding.message.TransactionRequestAssembler;

@Controller
public class TransactionLogic {
    
    private static final Logger log = LoggerFactory.getLogger(TransactionLogic.class);
    
    @Autowired
    private ApplicationConfiguration configuration;
    
    @Autowired
    private BinanceDexApiRestClient client;
    
    @Bean
    public BinanceDexApiRestClient getClient() {
        return BinanceDexApiClientFactory.newInstance().newRestClient(configuration.getBinanceDexEnvironment().getBaseUrl());
    }
    
    public String transfer(Wallet wallet, Transfer transfer, TransactionOption options) throws DexServiceException {
        wallet.ensureWalletIsReady(client);
        try {
            TransactionRequestAssembler assembler = new TransactionRequestAssembler(wallet, options);
            return assembler.buildTransferPayload(transfer);
        } catch (NoSuchAlgorithmException | IOException ex) {
            log.error("Transfer ex: {}", ex.getMessage(), ex);
            throw new DexServiceException(ex.getMessage(), ex);
        }
    }
}
