package org.humblemachine;


import org.humblemachine.executor.Node;
import org.humblemachine.models.NodeResult;
import org.humblemachine.models.Request;

import java.util.Observable;
import java.util.Optional;

public class Task extends Observable {

    private Node work;

    public Task(Node work) {
        super();
        this.work = work;
    }

    public void work(Request request) throws Exception {
        if(!work.process(request)){
            throw new Exception("Coudn't finish work succesfully");
        }
        notifyObservers(work.getOutput());
    }

    public Optional<NodeResult> getResult() {
        return Optional.ofNullable(work.done() ? work.getOutput() : null);
    }

}
