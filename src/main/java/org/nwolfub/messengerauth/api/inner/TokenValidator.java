package org.nwolfub.messengerauth.api.inner;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;

@Component
public class TokenValidator {
    private static String randomString;
    private static String[] symbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".split("");
    @EventListener(ContextRefreshedEvent.class)
    public static void initialize() {
        Random r = new Random();
        StringBuilder randomBuilder = new StringBuilder();
        for(int i = 0; i<50; i++) {
            randomBuilder.append(symbols[r.nextInt(symbols.length)]);
        }
        randomString = randomBuilder.toString();
        System.out.println("Initializated token validator!");
    }
}
