package org.nwolfub.messengerauth;

public class JsonBuilder {
    public static String loginResponse(boolean ok, String token, String error) {
        return "{\"ok\":" + ok + ",\"token\":\"" + token + "\"" + (ok?"":",\"error\":\"" + error + "\"") + "}";
    }
}
