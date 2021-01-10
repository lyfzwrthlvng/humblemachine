package org.humblemachine;

import org.humblemachine.models.ReqResultImpl;

import java.util.*;

public class WorkFlow implements Observer {
    private List<Task> tasks;
    private List<Task> firstTasks;

    public WorkFlow(Collection<Task> tasks, Collection<Task> firstTasks) {
        this.tasks = new LinkedList<>();
        this.tasks.addAll(tasks);
        this.firstTasks = new LinkedList<>();
        this.firstTasks.addAll(firstTasks);
    }

    public void start(ReqResultImpl request) {
        firstTasks.forEach(firstTask -> firstTask.update(null, request));
    }

    @Override
    public void update(Observable o, Object arg) {
        // the last update will be received here
        System.out.println("last one");
    }
}
