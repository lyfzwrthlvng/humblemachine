package org.humblemachine;

import org.humblemachine.executor.Node;
import org.humblemachine.models.NodeResult;
import org.humblemachine.models.Request;

import java.util.*;
import java.util.stream.Collectors;

public class Task2 extends Observable implements Observer {

    public static class TaskIdentifier {
        private String id;

        public TaskIdentifier(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    private TaskIdentifier identifier;
    private Node work;
    private Map<Task2, Optional<Object>> triggerArgs = new HashMap<>();

    public Task2(TaskIdentifier identifier) {
        super();
        this.identifier = identifier;
    }

    public Task2(TaskIdentifier identifier, Node work, List<Task2> observers, List<Task2> triggers){
        this(identifier);
        this.work = work;
        observers.forEach(this::addObserver);
        triggers.forEach(trigger -> triggerArgs.put(trigger, Optional.empty()));
    }

    public TaskIdentifier getIdentifier() {
        return this.identifier;
    }

    @Override
    public void update(Observable o, Object arg) {
        NodeResult result = (NodeResult) arg;
        Task2 observableTask = (Task2) o;
        triggerArgs.put(observableTask, Optional.ofNullable(result.getResultValue()));
        if (triggerArgs.values().stream().allMatch(Optional::isPresent)) {
            Request workRequest = new Request(triggerArgs.entrySet()
                    .stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get()))
            );
            work.process(workRequest);
            notifyObservers(work.getOutput());
        }
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof Task2) {
            return hashCode() == ((Task2)other).hashCode();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return identifier.hashCode(); // TODO add triggers as well
    }

}
