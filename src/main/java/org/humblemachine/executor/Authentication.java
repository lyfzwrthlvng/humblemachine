package org.humblemachine.executor;

import KC.entities.KCAccessRequest;
import KC.entities.NodeResult;

public class Authentication implements Node {

    @Override
    public boolean process(KCAccessRequest request) {
        // verify if user exists
        // and is authenticated in
        //return false;
        return true;
    }

    @Override
    public boolean process() {
        // verify if user exists
        // and is authenticated in
        return false;
    }

    @Override
    public NodeResult getOutput() {
        return null;
    }

    @Override
    public void setOutput(NodeResult result) {

    }

    @Override
    public String getResultNameForNode() {
        // Doesn't really need a result
        return "Auth";
    }

}
