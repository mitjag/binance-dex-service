package com.binance.dex.service.rest;

import java.io.Serializable;

import com.binance.dex.api.client.Wallet;

public class MultiWallet implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String name;
    
    private Integer pin;
    
    private Wallet wallet;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getPin() {
        return pin;
    }
    
    public void setPin(Integer pin) {
        this.pin = pin;
    }
    
    public Wallet getWallet() {
        return wallet;
    }
    
    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
