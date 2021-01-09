package org.humblemachine;

import org.humblemachine.models.NodeResult;
import org.humblemachine.models.Request;

import java.util.*;
import java.util.stream.Collectors;

public class Execution implements Observer {

    private Task myTask;

    private Map<String, Optional<Object>> triggerArgs = new HashMap<>();

    public Execution(Task task, List<String> triggers) {
        this.myTask = task;
        triggers.forEach(trigger -> {
            triggerArgs.put(trigger, Optional.empty());
        });
    }

    public void update(Observable o, Object arg) {
        // notification!
        NodeResult result = (NodeResult) arg;
        triggerArgs.put(result.getResultName(), Optional.ofNullable(result.getResultValue()));
        if (triggerArgs.values().stream().allMatch(Optional::isPresent)) {
            Request workRequest = new Request(triggerArgs.entrySet()
                    .stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get()))
            );
            try {
                myTask.work(workRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
            updateDone();
        }
    }

    public Map<String, Optional<Object>> getTriggerArgs() {
        return triggerArgs;
    }

    private void updateDone() {
    }
}
