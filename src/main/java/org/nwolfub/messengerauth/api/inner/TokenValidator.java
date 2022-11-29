package org.nwolfub.messengerauth.api.inner;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class TokenValidator {
    private static String randomString;
    private static String[] symbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".split("");
    private static MessageDigest digest;
    static {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

//    token format: userId:hash(userid + protectString)
    public static void initialize() {
        Random r = new Random();
        StringBuilder randomBuilder = new StringBuilder();
        for(int i = 0; i<50; i++) {
            randomBuilder.append(symbols[r.nextInt(symbols.length)]);
        }
        randomString = randomBuilder.toString();
        System.out.println("Initialized token validator!");
    }

    /**
     * validateToken is a function for checking whether token is generated originally on the server or by some other user
     * In other words, used to verify tokens
     * @param token - token to validate
     * @return result of validation
     */
    public static ValidationResult validateToken(String token) {
        try {
            Integer userId = Integer.valueOf(token.split(":")[0]);
            String uHash = hash(userId + randomString);
            if(uHash.equals(token.split(":")[1])) {
                return new ValidationResult(true, userId);
            }
        } catch (Exception ignored) {}
        return new ValidationResult(false, null);
    }

    /**
     * Hashes string with SHA-256
     * @param string - string to hash
     * @return hashed string
     */
    private static String hash(String string) {
        return DatatypeConverter.printBase64Binary(digest.digest(string.getBytes()));
    }

    public static String makeToken(Integer userId) {
        return userId + hash(userId + randomString);
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
