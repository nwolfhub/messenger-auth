package org.nwolfub.messengerauth.api.inner;

import org.nwolfub.messengerauth.Utils;
import org.nwolfub.messengerauth.config.DatabaseConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.IOException;

@Component
public class TokenCommunicator {

    @Autowired
    private RedisConnectionData redisData;

    public boolean isUsed = true;

    private Jedis jedis;

    public TokenCommunicator(RedisConnectionData redisData) throws IOException {
        this.redisData = redisData;
        if(!this.redisData.useRedis) {
            isUsed = false;
        }
        else {
            JedisPool pool = new JedisPool(this.redisData.getUrl(), this.redisData.getPort());
            //jedis = new Jedis(this.redisData.getUrl(), this.redisData.getPort());
            jedis = pool.getResource();
            if(this.redisData.isUsePassword()) jedis.auth(this.redisData.getPassword());
            if(!jedis.isConnected()) throw new IOException("Could not connect to redis server!");
            System.out.println("Jedis up and running!");
        }
    }

    /**
     * Looking for user who owns token
     * @param token user's token
     * @return id of a user
     * @throws NullPointerException in case token is not found
     */
    public Integer auth(String token) throws NullPointerException{
        if(redisData.useRedis) {
            return Integer.valueOf(jedis.get(token));
        } else {
            try {
                TokenValidator.ValidationResult result = TokenValidator.validateToken(token);
                if (result.isOk()) return result.getUser();
                else throw new NullPointerException("Token not found!");
            } catch (JedisConnectionException e) {
                JedisPool pool = new JedisPool(this.redisData.getUrl(), this.redisData.getPort());
                jedis = pool.getResource();
                return authNoRecursion(token);
            }
        }
    }
    private Integer authNoRecursion(String token) throws NullPointerException{
        if(redisData.useRedis) {

            return Integer.valueOf(jedis.get(token));
        } else {
            TokenValidator.ValidationResult result = TokenValidator.validateToken(token);
            if (result.isOk()) return result.getUser();
            else throw new NullPointerException("Token not found!");
        }
    }
    public void test() {
        jedis.set("test", "test");
    }

    /**
     * Provides a newly generated token. If redis is not used same user will have same token while randomstring is not changed
     * @param userId -  id of a user to generate token for
     * @return generated token
     */
    public String makeToken(Integer userId) {
        try {
            if (redisData.useRedis) {
                String token = "NWOLF" + Utils.generateString(50) + "HUB";
                String userTokens = jedis.get(userId.toString());
                if(userTokens == null) {
                    userTokens = token;
                } else if(userTokens.equals("")) {
                    userTokens = token;
                } else {
                    userTokens = userTokens + ";" + token;
                }
                jedis.set(userId.toString(), userTokens);
                jedis.set(token, userId.toString());
                return token;
            } else {
                return TokenValidator.makeToken(userId);
            }
        } catch (JedisConnectionException e) {
            JedisPool pool = new JedisPool(this.redisData.getUrl(), this.redisData.getPort());
            jedis = pool.getResource();
            return makeTokenNoRecursion(userId);
        }
    }

    private String makeTokenNoRecursion(Integer userId) {
        if (redisData.useRedis) {
            String token = "NWOLF" + Utils.generateString(50) + "HUB";
            String userTokens = jedis.get(userId.toString());
            if(userTokens == null) {
                userTokens = token;
            } else if(userTokens.equals("")) {
                userTokens = token;
            } else {
                userTokens = userTokens + ";" + token;
            }
            jedis.set(userId.toString(), userTokens);
            jedis.set(token, userId.toString());
            return token;
        } else {
            return TokenValidator.makeToken(userId);
        }
    }

    public static class RedisNotUsedException extends Exception {
        public RedisNotUsedException(String text) {
            super(text);
        }
    }
    public static class RedisConnectionData {
        public boolean useRedis;
        public String url;
        public Integer port;
        public boolean usePassword;
        public String password;

        public RedisConnectionData() {}

        public boolean isUseRedis() {
            return useRedis;
        }

        public RedisConnectionData setUseRedis(boolean useRedis) {
            this.useRedis = useRedis;
            return this;
        }

        public String getUrl() {
            return url;
        }

        public RedisConnectionData setUrl(String url) {
            this.url = url;
            return this;
        }

        public Integer getPort() {
            return port;
        }

        public RedisConnectionData setPort(Integer port) {
            this.port = port;
            return this;
        }

        public boolean isUsePassword() {
            return usePassword;
        }

        public RedisConnectionData setUsePassword(boolean usePassword) {
            this.usePassword = usePassword;
            return this;
        }

        public String getPassword() {
            return password;
        }

        public RedisConnectionData setPassword(String password) {
            this.password = password;
            return this;
        }
    }
}
