package org.humblemachine.models;

import org.humblemachine.TaskIdentifier;

import java.util.HashMap;
import java.util.Map;

public class ReqResultImpl implements ReqResult {
    private Map<TaskIdentifier, Object> args;

    public ReqResultImpl(Map<TaskIdentifier, Object> args) {
        this.args = args;
    }

    public ReqResultImpl(String tid,  Object value) {
        Map<TaskIdentifier, Object> result = new HashMap<>();
        result.put(new TaskIdentifier(tid), value);
        this.args = result;
    }

    public Map<TaskIdentifier, Object> getArgs() {
        return args;
    }
}
