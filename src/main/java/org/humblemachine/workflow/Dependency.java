package org.humblemachine.workflow;

import org.humblemachine.workflow.TaskIdentifier;

public class Dependency {
    private TaskIdentifier obserable;
    private TaskIdentifier observer;

    public Dependency(TaskIdentifier obserable, TaskIdentifier observer) {
        this.obserable = obserable;
        this.observer = observer;
    }

    public TaskIdentifier getObserable() {
        return obserable;
    }

    public TaskIdentifier getObserver() {
        return observer;
    }
}
