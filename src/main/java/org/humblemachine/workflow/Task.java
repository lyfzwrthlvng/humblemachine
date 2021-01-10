package org.humblemachine.workflow;

import org.humblemachine.executor.ExecutionHelper;
import org.humblemachine.executor.Callback;
import org.humblemachine.executor.Node;
import org.humblemachine.models.ReqResult;
import org.humblemachine.models.ReqResultImpl;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class Task extends Observable implements Observer {
    private TaskIdentifier identifier;
    private ExecutorService executor;
    private ExecutionHelper helper;
    private Map<TaskIdentifier, Optional<Object>> triggerArgs = new HashMap<>();

    public Task(TaskIdentifier identifier, ExecutorService executor, Node node) {
        super();
        this.identifier = identifier;
        this.executor = executor;
        this.helper = new ExecutionHelper(node, new TaskCallback());
    }

    public TaskIdentifier getIdentifier() {
        return this.identifier;
    }

    public void addDependency(Dependency dependency) {
        if(dependency.getObserver().equals(this.getIdentifier())) {
            triggerArgs.put(dependency.getObserable(), Optional.empty());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        ReqResult result = ((ReqResult) arg);
        if(o != null) {
            Task observableTask = (Task) o;
            triggerArgs.put(observableTask.getIdentifier(), Optional.ofNullable(result));
        }
        if (triggerArgs.values().stream().allMatch(Optional::isPresent)) {
            ReqResultImpl workRequest = new ReqResultImpl(triggerArgs.entrySet()
                    .stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
            helper.setInput(workRequest);
            executor.submit(helper);
        }
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof Task) {
            return hashCode() == ((Task)other).hashCode();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return identifier.hashCode(); // TODO add triggers as well
    }

    class TaskCallback implements Callback {
        @Override
        public void complete(ReqResult result) {
            setChanged();
            notifyObservers(helper.getResult());
        }
    }
}
