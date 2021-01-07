package org.humblemachine.models;

import java.util.Map;

public class Request {
    private Map<String, Object> args;

    public Request(Map<String, Object> args) {
        this.args = args;
    }

    public Map<String, Object> getArgs() {
        return args;
    }
}
