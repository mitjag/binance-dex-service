package com.binance.dex.service.rest;

import java.io.Serializable;

public class ApplicationConfigurationWallet implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String name;
    
    private Integer pin;
    
    private String phrase;
    
    private String privateKey;
    
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
    
    public String getPhrase() {
        return phrase;
    }
    
    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }
    
    public String getPrivateKey() {
        return privateKey;
    }
    
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
