package com.binance.dex.service.rest;

import java.io.Serializable;

import com.binance.dex.api.client.Wallet;

public class ApplicationConfiguration implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Wallet wallet;
    
    public Wallet getWallet() {
        return wallet;
    }
    
    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
