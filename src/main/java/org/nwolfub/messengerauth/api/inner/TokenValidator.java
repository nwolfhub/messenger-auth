package org.nwolfub.messengerauth.api.inner;

import java.util.Random;

public class TokenValidator {
    private static String randomString;
    private static String[] symbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".split("");

//    token format: userId:hash(userid + protectString);till:hash(till + protectString)
    public static void initialize() {
        Random r = new Random();
        StringBuilder randomBuilder = new StringBuilder();
        for(int i = 0; i<50; i++) {
            randomBuilder.append(symbols[r.nextInt(symbols.length)]);
        }
        randomString = randomBuilder.toString();
        System.out.println("Initialized token validator!");
    }

    public static ValidationResult validateToken(String token) {
        try {
            String firstPart = token.split(";")[0];
            String secondPart = token.split(";")[1];
            String uid = firstPart.split(":")[0];
            String time = secondPart.split(":")[0];
        }
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
