package org.nwolfhub.shared;

import java.util.HashMap;

public class LimitController { //todo

    public HashMap<String, Integer> reputation;
    public LimitController() {
        reputation = new HashMap<>();
    }

    public void addRep(String ip, Integer reputation) {

    }

    public boolean isAllowed(String ip) {
        return true;
    }
}
