package org.humblemachine.models;

import org.humblemachine.workflow.TaskIdentifier;

import java.util.Map;

public interface ReqResult {
    public Object getValue(String inputKey);
    public Object getValue(TaskIdentifier taskId, String inputKey);
    public Map<String, Object> getArgs();
}
