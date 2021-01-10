package org.humblemachine;

import org.humblemachine.executor.Callback;
import org.humblemachine.executor.Node;
import org.humblemachine.models.ReqResult;

public class ExecutionHelper implements Runnable {
    private Node node;
    private Callback callback;
    private ReqResult input;
    private ReqResult result;

    public ExecutionHelper(Node node, Callback callback) {
        this.node = node;
        this.callback = callback;
    }

    public void setInput(ReqResult request) {
        this.input = request;
    }

    @Override
    public void run() {
        this.result = node.work(this.input);
        callback.complete(this.result);
    }

    public ReqResult getResult() {
        return result;
    }
}
