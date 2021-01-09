package org.humblemachine.models;

import org.humblemachine.Task2;
import java.util.Map;

public class Request {
    private Map<Task2, Object> args;

    public Request(Map<Task2, Object> args) {
        this.args = args;
    }

    public Map<Task2, Object> getArgs() {
        return args;
    }
}
