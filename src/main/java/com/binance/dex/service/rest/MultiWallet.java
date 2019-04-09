package com.binance.dex.service.rest;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.Wallet;

public class MultiWallet implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String name;
    
    private Integer pin;
    
    private List<String> ipWhitelist;
    
    private Wallet wallet;
    
    /**
     * 
     * @param name
     * @param pin
     * @param ipWhitelist comma separated IP whitelist
     * @param phrase phrase or privateKey has to be present (phrase has priority over private key)
     * @param privateKey if phrase is null private key is used
     * @param env
     * @return
     * @throws IOException
     */
    public static MultiWallet createMultiWallet(String name, int pin, String ipWhitelist, String phrase, String privateKey, BinanceDexEnvironment env) throws IOException {
        MultiWallet multiWallet = new MultiWallet();
        multiWallet.setName(name);
        multiWallet.setPin(pin);
        multiWallet.setIpWhitelist(Arrays.asList(ipWhitelist.split(",")));
        if (phrase != null) {
            multiWallet.setWallet(Wallet.createWalletFromMnemonicCode(Arrays.asList(phrase.split(" ")), env));
        } else {
            multiWallet.setWallet(new Wallet(privateKey, env));
        }
        return multiWallet;
    }
    
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
    
    public List<String> getIpWhitelist() {
        return ipWhitelist;
    }
    
    public void setIpWhitelist(List<String> ipWhitelist) {
        this.ipWhitelist = ipWhitelist;
    }
    
    public Wallet getWallet() {
        return wallet;
    }
    
    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
