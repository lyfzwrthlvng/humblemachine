package org.humblemachine.executor;

import KC.constants.*;
import KC.entities.KCAccessRequest;
import KC.entities.NodeResult;

import java.util.ArrayList;
import java.util.HashMap;

public class Machine {

    // The machine that stitches together different components and executes any request
    ArrayList<Node> executionList = null;
    EndService service = null;
    KCAccessRequest request = null;
    HashMap<String, Object> output = null;

    public Machine(ArrayList<Node> executionList, EndService service, KCAccessRequest request) {
        this.executionList = executionList;
        this.service = service;
        this.request = request;
    }

    public void populateExecutionList(ArrayList<Node> executionList) {
        this.executionList = executionList;
    }

    public boolean execute() {
        if( this.executionList == null ) {
            return false;
        }
        output = new HashMap<>();
        for(Node executionStep : executionList) {
            if( !executionStep.process(request) ) {
                return false;
            } else {
                NodeResult result = executionStep.getOutput();
                if(result != null) {
                    output.putAll(result.getResult());
                }
            }
        }
        return true;
    }

    public HashMap<String, Object> getResult() {
        return output;
    }
}