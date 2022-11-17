package org.nwolfub.messengerauth.api.inner;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;

public class TokenValidator {
    private static String randomString;
    private static String[] symbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".split("");

//    token format: userId:hash(userid + protectString )
    public static void initialize() {
        Random r = new Random();
        StringBuilder randomBuilder = new StringBuilder();
        for(int i = 0; i<50; i++) {
            randomBuilder.append(symbols[r.nextInt(symbols.length)]);
        }
        randomString = randomBuilder.toString();
        System.out.println("Initialized token validator!");
    }

    public static  validationResult validateToken(String token)

    }

    public static class validationResult  {
        public boolean ok;
        public Integer user;

        public validationResult() {}

        public validationResult(boolean ok, Integer user) {
            this.ok = ok;
            this.user = user;
        }

        public boolean isOk() {
            return ok;
        }

        public validationResult setOk(boolean ok) {
            this.ok = ok;
            return this;
        }

        public Integer getUser() {
            return user;
        }

        public validationResult setUser(Integer user) {
            this.user = user;
            return this;
        }
    }
}
