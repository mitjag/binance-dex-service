package com.binance.dex.service.rest;

import java.io.IOException;
import java.util.Arrays;

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
import com.binance.dex.api.client.Wallet;

@SpringBootApplication
public class Application implements ApplicationRunner {
    
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    
    @Autowired
    private Environment env;
    
    @Value("${dex.env:TEST_NET}")
    private String dexEnv;
    
    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Application started with command-line arguments: {}", Arrays.toString(args.getSourceArgs()));
        log.info("NonOptionArgs: {}", args.getNonOptionArgs());
        log.info("OptionNames: {}", args.getOptionNames());

        for (String name : args.getOptionNames()){
            log.info("arg-" + name + "=" + args.getOptionValues(name));
        }

        boolean containsOption = args.containsOption("dexEnv");
        log.info("Contains dex.env: " + containsOption);
    }
    
    @Bean
    public ApplicationConfiguration endpointUri() {
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration();
        if (dexEnv.equalsIgnoreCase("PROD")) {
            applicationConfiguration.setBinanceDexEnvironment(BinanceDexEnvironment.PROD);
        } else {
            applicationConfiguration.setBinanceDexEnvironment(BinanceDexEnvironment.TEST_NET);
        }
        ApplicationConfigurationWallet wallet = env.getProperty("dex.wallet", ApplicationConfigurationWallet.class);
        MultiWallet multiWallet = new MultiWallet();
        multiWallet.setName(wallet.getName());
        multiWallet.setPin(wallet.getPin());
        try {
            multiWallet.setWallet(Wallet.createWalletFromMnemonicCode(Arrays.asList(wallet.getPhrase().split(" ")), applicationConfiguration.getBinanceDexEnvironment()));
            applicationConfiguration.loadWallets(Arrays.asList(multiWallet));
        } catch (IOException ex) {
            log.error("IOException ex: {}", ex.getMessage(), ex);
        }
        return applicationConfiguration;
    }
}
