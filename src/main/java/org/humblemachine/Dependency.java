package org.humblemachine;

public class Dependency {
    private Task2.TaskIdentifier obserable;
    private Task2.TaskIdentifier observer;

    public Dependency(Task2.TaskIdentifier obserable, Task2.TaskIdentifier observer) {
        this.obserable = obserable;
        this.observer = observer;
    }

    public Task2.TaskIdentifier getObserable() {
        return obserable;
    }

    public Task2.TaskIdentifier getObserver() {
        return observer;
    }
}
