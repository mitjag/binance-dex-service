package com.binance.dex.service.rest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import com.binance.dex.api.client.BinanceDexEnvironment;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class Application implements ApplicationRunner {
    
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    
    @Autowired
    private Environment env;
    
    @Value("${env:TEST_NET}")
    private String dexEnv;
    
    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*log.info("Application started with command line arguments: {}", Arrays.toString(args.getSourceArgs()));
        for (String name : args.getOptionNames()) {
            log.info("arg " + name + "=" + args.getOptionValues(name));
        }*/
    }
    
    @Bean
    public ApplicationConfiguration loadConfiguration() {
        ApplicationConfiguration configuration = new ApplicationConfiguration();
        if (dexEnv.equalsIgnoreCase("PROD")) {
            configuration.setBinanceDexEnvironment(BinanceDexEnvironment.PROD);
        } else {
            configuration.setBinanceDexEnvironment(BinanceDexEnvironment.TEST_NET);
        }
        
        String walletFile = env.getProperty("wallet.file");
        if (walletFile == null) {
            // load single wallet from command line arguments
            String walletName = env.getProperty("wallet.name");
            if (walletName == null) {
                throw new IllegalArgumentException("wallet.name missing");
            }
            String walletPinString = env.getProperty("wallet.pin");
            if (walletPinString == null) {
                throw new IllegalArgumentException("wallet.pin missing");
            }
            int walletPin = Integer.parseInt(walletPinString);
            String walletPhrase = env.getProperty("wallet.phrase");
            String walletPrivateKey = env.getProperty("wallet.privateKey");
            if (walletPhrase == null && walletPrivateKey == null) {
                throw new IllegalArgumentException("wallet.phrase or wallet.privateKey missing");
            }
            
            try {
                MultiWallet multiWallet = MultiWallet.createMultiWallet(walletName, walletPin, walletPhrase, walletPrivateKey, configuration.getBinanceDexEnvironment());
                configuration.loadWallets(Arrays.asList(multiWallet));
                log.info("Wallet loaded name: {} address: {}", multiWallet.getName(), multiWallet.getWallet().getAddress());
            } catch (IOException ex) {
                log.error("IOException ex: {}", ex.getMessage(), ex);
                throw new IllegalArgumentException(ex.getMessage());
            }
            
        } else {
            log.info("Loading wallet.file ignoring wallet from command line arguments");
            ObjectMapper om = new ObjectMapper();
            try {
                List<ApplicationConfigurationWallet> wallets = om.readValue(new File(walletFile), new TypeReference<List<ApplicationConfigurationWallet>>() {});
                List<MultiWallet> multiWallets = new ArrayList<MultiWallet>();
                for (ApplicationConfigurationWallet wallet : wallets) {
                    MultiWallet multiWallet = MultiWallet.createMultiWallet(wallet.getName(), wallet.getPin(), wallet.getPhrase(), wallet.getPrivateKey(), configuration.getBinanceDexEnvironment());
                    multiWallets.add(multiWallet);
                    log.info("Wallet loaded name: {} address: {}", multiWallet.getName(), multiWallet.getWallet().getAddress());
                }
            } catch (IOException ex) {
                log.error("IOException ex: {}", ex.getMessage(), ex);
                throw new IllegalArgumentException(ex.getMessage());
            }
        }
        return configuration;
    }
}
