package org.humblemachine.models;

import org.humblemachine.workflow.Task;
import org.humblemachine.workflow.TaskIdentifier;

import java.util.HashMap;
import java.util.Map;

public class ReqResultImpl implements ReqResult {
    private Map<String, Object> args;

    public ReqResultImpl(Map<String, Object> args) {
        this.args = args;
    }

    public ReqResultImpl(String tid,  Object value) {
        Map<String, Object> result = new HashMap<>();
        result.put(tid, value);
        this.args = result;
    }

    public Map<String, Object> getArgs() {
        return args;
    }

    @Override
    public Object getValue(String inputKey) {
        return getValue(Task.EMPTY_TASK.getIdentifier(), inputKey);
    }

    @Override
    public Object getValue(TaskIdentifier taskId, String inputKey) {
        return ((Map)args.get(taskId.getId())).get(inputKey);
    }
}
