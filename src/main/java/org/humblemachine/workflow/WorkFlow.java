package org.humblemachine.workflow;

import org.humblemachine.executor.Callback;
import org.humblemachine.models.ReqResult;
import org.humblemachine.models.ReqResultImpl;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class WorkFlow implements Observer {
    private List<Task> tasks;
    private List<Task> firstTasks;
    private final Map<TaskIdentifier, Optional<ReqResult>> triggerArgs = new HashMap<>();
    private Optional<Callback> callbackOptional;

    public WorkFlow(Collection<Task> tasks, Collection<Task> firstTasks, Callback callback) {
        this.tasks = new LinkedList<>();
        this.tasks.addAll(tasks);
        this.firstTasks = new LinkedList<>();
        this.firstTasks.addAll(firstTasks);
        this.callbackOptional = Optional.ofNullable(callback);
    }

    public void start(ReqResult request) {
        firstTasks.forEach(firstTask -> firstTask.update(Task.EMPTY_TASK, request));
    }

    @Override
    public void update(Observable o, Object arg) {
        // the last update will be received here
        callbackOptional.ifPresent( callback -> {
            ReqResult result = ((ReqResult) arg);
            if (o != null) {
                Task observableTask = (Task) o;
                triggerArgs.put(observableTask.getIdentifier(), Optional.ofNullable(result));
            }
            if (triggerArgs.values().stream().allMatch(Optional::isPresent)) {
                ReqResultImpl workRequest = new ReqResultImpl(triggerArgs.entrySet()
                        .stream()
                        .collect(Collectors.toMap(e -> e.getKey().getId(), e -> e.getValue().get().getArgs())));
                callback.complete(workRequest);
            }
        });
    }
}
