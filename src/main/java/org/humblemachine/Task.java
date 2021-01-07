package org.humblemachine;


import org.humblemachine.executor.Node;
import org.humblemachine.models.NodeResult;
import org.humblemachine.models.Request;

import java.util.Observable;

public class Task extends Observable {

    private Node work;

    public Task(Node work) {
        super();
        this.work = work;
    }

    public NodeResult work(Request request) throws Exception {
        if(!work.process(request)){
            throw new Exception("Coudn't finish work succesfully");
        }
        return work.getOutput();
    }

}
