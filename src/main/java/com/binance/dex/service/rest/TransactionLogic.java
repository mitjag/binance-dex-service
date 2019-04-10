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
import com.binance.dex.api.client.domain.broadcast.CancelOrder;
import com.binance.dex.api.client.domain.broadcast.MultiTransfer;
import com.binance.dex.api.client.domain.broadcast.NewOrder;
import com.binance.dex.api.client.domain.broadcast.TokenFreeze;
import com.binance.dex.api.client.domain.broadcast.TokenUnfreeze;
import com.binance.dex.api.client.domain.broadcast.TransactionOption;
import com.binance.dex.api.client.domain.broadcast.Transfer;
import com.binance.dex.api.client.domain.broadcast.Vote;
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
    
    public String newOrder(Wallet wallet, NewOrder newOrder, TransactionOption options) throws DexServiceException {
        wallet.ensureWalletIsReady(client);
        try {
            TransactionRequestAssembler assembler = new TransactionRequestAssembler(wallet, options);
            return assembler.buildNewOrderPayload(newOrder);
        } catch (NoSuchAlgorithmException | IOException ex) {
            log.error("New order ex: {}", ex.getMessage(), ex);
            throw new DexServiceException(ex.getMessage(), ex);
        }
    }
    
    public String vote(Wallet wallet, Vote vote, TransactionOption options) throws DexServiceException {
        wallet.ensureWalletIsReady(client);
        try {
            TransactionRequestAssembler assembler = new TransactionRequestAssembler(wallet, options);
            return assembler.buildVotePayload(vote);
        } catch (NoSuchAlgorithmException | IOException ex) {
            log.error("Vote ex: {}", ex.getMessage(), ex);
            throw new DexServiceException(ex.getMessage(), ex);
        }
    }
    
    public String cancelOrder(Wallet wallet, CancelOrder cancelOrder, TransactionOption options) throws DexServiceException {
        wallet.ensureWalletIsReady(client);
        try {
            TransactionRequestAssembler assembler = new TransactionRequestAssembler(wallet, options);
            return assembler.buildCancelOrderPayload(cancelOrder);
        } catch (NoSuchAlgorithmException | IOException ex) {
            log.error("Cancel order ex: {}", ex.getMessage(), ex);
            throw new DexServiceException(ex.getMessage(), ex);
        }
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
    
    public String multiTransfer(Wallet wallet, MultiTransfer multiTransfer, TransactionOption options) throws DexServiceException {
        wallet.ensureWalletIsReady(client);
        try {
            TransactionRequestAssembler assembler = new TransactionRequestAssembler(wallet, options);
            return assembler.buildMultiTransferPayload(multiTransfer);
        } catch (NoSuchAlgorithmException | IOException ex) {
            log.error("Multi transfer ex: {}", ex.getMessage(), ex);
            throw new DexServiceException(ex.getMessage(), ex);
        }
    }
    
    public String freeze(Wallet wallet, TokenFreeze tokenFreeze, TransactionOption options) throws DexServiceException {
        wallet.ensureWalletIsReady(client);
        try {
            TransactionRequestAssembler assembler = new TransactionRequestAssembler(wallet, options);
            return assembler.buildTokenFreezePayload(tokenFreeze);
        } catch (NoSuchAlgorithmException | IOException ex) {
            log.error("Token freeze ex: {}", ex.getMessage(), ex);
            throw new DexServiceException(ex.getMessage(), ex);
        }
    }
    
    public String unfreeze(Wallet wallet, TokenUnfreeze tokenUnfreeze, TransactionOption options) throws DexServiceException {
        wallet.ensureWalletIsReady(client);
        try {
            TransactionRequestAssembler assembler = new TransactionRequestAssembler(wallet, options);
            return assembler.buildTokenUnfreezePayload(tokenUnfreeze);
        } catch (NoSuchAlgorithmException | IOException ex) {
            log.error("Token unfreeze ex: {}", ex.getMessage(), ex);
            throw new DexServiceException(ex.getMessage(), ex);
        }
    }
}
