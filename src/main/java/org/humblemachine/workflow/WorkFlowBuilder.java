package org.humblemachine.workflow;

import org.humblemachine.executor.Callback;
import org.humblemachine.executor.Node;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class WorkFlowBuilder {
    private Map<TaskIdentifier, Task> taskMap;
    private Map<TaskIdentifier, List<TaskIdentifier>> dep;
    private ExecutorService executorService;
    private Callback callback;

    public WorkFlowBuilder() {
        this.taskMap = new HashMap<>();
        this.dep = new HashMap<>();
    }

    public WorkFlowBuilder withExecutor(ExecutorService executor) {
        this.executorService = executor;
        return this;
    }

    public WorkFlowBuilder addWork(Node node, String identifier) {
        Task task = new Task(new TaskIdentifier(identifier), this.executorService, node);
        addTask(task);
        return this;
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

    public WorkFlowBuilder withCallback(Callback callback) {
        this.callback = callback;
        return this;
    }

    public WorkFlow build() throws Exception {
        List<Task> leafTasks = validateAndGetLeafTask();
        Set<TaskIdentifier> secTaskSet = new HashSet<>();
        dep.values().forEach(secTaskSet::addAll);
        List<Task> firstTasks = this.taskMap.values().stream()
                .filter(task -> !secTaskSet.contains(task.getIdentifier()))
                .collect(Collectors.toList());
        firstTasks.forEach(firstTask -> firstTask.addDependency(new Dependency(Task.EMPTY_TASK.getIdentifier(),
                firstTask.getIdentifier())));
        WorkFlow wf = new WorkFlow(taskMap.values(), firstTasks, this.callback);
        if(this.callback != null) {
            leafTasks.forEach(leafTask -> leafTask.addObserver(wf));
        }
        return wf;
    }

    private void validateDependency(Dependency dependency) throws Exception {
        TaskIdentifier observer = dependency.getObserver();
        TaskIdentifier observable = dependency.getObserable();
        if(dep.containsKey(observer) && dep.get(observer).contains(observable)) {
            throw new Exception("Invalid dependency " + observer + "->" + observable);
        }
    }

    private List<Task> validateAndGetLeafTask() throws Exception {
        // there should be atleast one task with no observable
        if(dep.values().containsAll(taskMap.keySet())) {
            throw new Exception("validation failed, no first task found!");
        }
        // similarly there should be some tasks with no observer
        List<Task> leafTasks = taskMap.values().stream()
                .filter(task -> !dep.keySet().contains(task.getIdentifier()))
                .collect(Collectors.toList());
        if(leafTasks.size()==0) {
            throw  new Exception("Workflow will never end!");
        }
        return leafTasks;
    }
}
