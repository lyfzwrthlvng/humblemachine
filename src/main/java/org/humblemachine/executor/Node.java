package org.humblemachine.executor;


import org.humblemachine.models.NodeResult;
import org.humblemachine.models.Request;

public interface Node {
    public boolean process(Request request);
    public NodeResult getOutput();
    public void setOutput(NodeResult result);
    String getResultNameForNode();
    public boolean done();
}
