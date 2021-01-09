package org.humblemachine;

import java.util.*;

public class WorkFlow {
    private List<Task2> tasks;

    public WorkFlow(Collection<Task2> tasks) {
        this.tasks = new LinkedList<>();
        this.tasks.addAll(tasks);
    }

    public static class WorkFlowBuilder {
        Map<Task2.TaskIdentifier, Task2> taskMap;

        public WorkFlowBuilder() {
            this.taskMap = new HashMap<>();
        }

        public WorkFlowBuilder addTask(Task2 ft) {
            taskMap.put(ft.getIdentifier(), ft);
            return this;
        }

        public WorkFlowBuilder addTasks(Collection<Task2> ft) {
            ft.forEach( task -> taskMap.put(task.getIdentifier(), task));
            return this;
        }

        public WorkFlowBuilder addDependency(Dependency dependency) {
            this.taskMap.get(dependency.getObserable()).addObserver(this.taskMap.get(dependency.getObserver()));
            return this;
        }

        public WorkFlow build() {
            return new WorkFlow(taskMap.values());
        }
    }
}
