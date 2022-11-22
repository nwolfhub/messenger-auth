package org.nwolfub.messengerauth.api.inner;

import java.util.Random;

public class TokenValidator {
    private static String randomString;
    private static String[] symbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".split("");

//    token format: userId:hash(userid + protectString);id:hash(id + protectString)
    public static void initialize() {
        Random r = new Random();
        StringBuilder randomBuilder = new StringBuilder();
        for(int i = 0; i<50; i++) {
            randomBuilder.append(symbols[r.nextInt(symbols.length)]);
        }
        randomString = randomBuilder.toString();
        System.out.println("Initialized token validator!");
    }


    public static class ValidationResult {
        public boolean ok;
        public Integer user;

        public ValidationResult() {}

        public ValidationResult(boolean ok, Integer user) {
            this.ok = ok;
            this.user = user;
        }

        public boolean isOk() {
            return ok;
        }

        public ValidationResult setOk(boolean ok) {
            this.ok = ok;
            return this;
        }

        public Integer getUser() {
            return user;
        }

        public ValidationResult setUser(Integer user) {
            this.user = user;
            return this;
        }
    }
}
