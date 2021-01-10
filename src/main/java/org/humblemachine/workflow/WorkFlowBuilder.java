package org.humblemachine.workflow;

import java.util.*;
import java.util.stream.Collectors;

public class WorkFlowBuilder {
    private Map<TaskIdentifier, Task> taskMap;
    private Map<TaskIdentifier, List<TaskIdentifier>> dep;

    public WorkFlowBuilder() {
        this.taskMap = new HashMap<>();
        this.dep = new HashMap<>();
    }

    public WorkFlowBuilder addTask(Task ft) {
        taskMap.put(ft.getIdentifier(), ft);
        return this;
    }

    public WorkFlowBuilder addTasks(Collection<Task> ft) {
        ft.forEach( task -> taskMap.put(task.getIdentifier(), task));
        return this;
    }

    public WorkFlowBuilder addDependency(Dependency dependency) throws Exception {
        validateDependency(dependency);
        if(!this.dep.containsKey(dependency.getObserable())) {
            this.dep.put(dependency.getObserable(), new ArrayList<>());
        }
        this.dep.get(dependency.getObserable()).add(dependency.getObserver());
        this.taskMap.get(dependency.getObserable()).addObserver(this.taskMap.get(dependency.getObserver()));
        this.taskMap.get(dependency.getObserver()).addDependency(dependency);
        return this;
    }

    public WorkFlow build() throws Exception {
        validate();
        Set<TaskIdentifier> secTaskSet = new HashSet<>();
        dep.values().forEach(secTaskSet::addAll);
        List<Task> firstTasks = this.taskMap.values().stream()
                .filter(task -> !secTaskSet.contains(task.getIdentifier()))
                .collect(Collectors.toList());

        return new WorkFlow(taskMap.values(), firstTasks);
    }

    private void validateDependency(Dependency dependency) throws Exception {
        TaskIdentifier observer = dependency.getObserver();
        TaskIdentifier observable = dependency.getObserable();
        if(dep.containsKey(observer) && dep.get(observer).contains(observable)) {
            throw new Exception("Invalid dependency " + observer + "->" + observable);
        }
    }

    private void validate() throws Exception {
        // there should be atleast one task with no observable
        if(dep.values().containsAll(taskMap.keySet())) {
            throw new Exception("validation failed, no first task found!");
        }
    }
}
