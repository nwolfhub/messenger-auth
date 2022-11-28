package org.nwolfub.messengerauth.api.inner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

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
            jedis = new Jedis(this.redisData.getUrl(), this.redisData.getPort());
            if(this.redisData.isUsePassword()) jedis.auth(this.redisData.getPassword());
            if(!jedis.isConnected()) throw new IOException("Could not connect to redis server!");
            System.out.println("Jedis up and running!");
        }
    }

    public String auth(String token) {
        if(redisData.useRedis) {
            String user = jedis.get(token);
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
