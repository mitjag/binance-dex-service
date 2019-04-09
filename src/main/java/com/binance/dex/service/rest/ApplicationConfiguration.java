package com.binance.dex.service.rest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.Wallet;

public class ApplicationConfiguration implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Map<String, MultiWallet> wallets;
    
    private BinanceDexEnvironment binanceDexEnvironment;
    
    public void loadWallets(List<MultiWallet> wallets) {
        this.wallets = new HashMap<>();
        if (wallets == null) {
            return;
        }
        for (MultiWallet wallet : wallets) {
            this.wallets.put(wallet.getName(), wallet);
        }
    }
    
    public Wallet getWallet(String name, Integer pin, String ip) throws DexServiceException {
        MultiWallet multiWallet = wallets.get(name);
        if (pin == null) {
            throw new DexServiceException("Argument pin is null");
        }
        if (ip == null) {
            throw new DexServiceException("Argument ip is null");
        }
        if (multiWallet == null) {
            throw new DexServiceException("Wallet not found name: " + name);
        }
        if (pin.equals(multiWallet.getPin()) && multiWallet.getIpWhitelist().contains(ip)) {
            return multiWallet.getWallet();
        } else {
            throw new DexServiceException("Wrong pin or ip");
        }
    }
    
    public Map<String, MultiWallet> getWallets() {
        return wallets;
    }

    public void setWallets(Map<String, MultiWallet> wallets) {
        this.wallets = wallets;
    }
    
    public BinanceDexEnvironment getBinanceDexEnvironment() {
        return binanceDexEnvironment;
    }
    
    public void setBinanceDexEnvironment(BinanceDexEnvironment binanceDexEnvironment) {
        this.binanceDexEnvironment = binanceDexEnvironment;
    }
}
