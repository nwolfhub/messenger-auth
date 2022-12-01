package org.nwolfhub.shared;

import java.io.Serializable;

public class DataUnit implements Serializable {
    public String request;
    public String response;
    public Object other;


    public DataUnit() {}

    public String getRequest() {
        return request;
    }

    public DataUnit setRequest(String request) {
        this.request = request;
        return this;
    }

    public String getResponse() {
        return response;
    }

    public DataUnit setResponse(String response) {
        this.response = response;
        return this;
    }

    public Object getOther() {
        return other;
    }

    public DataUnit setOther(Object other) {
        this.other = other;
        return this;
    }
}
