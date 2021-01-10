package org.humblemachine.executor;

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
        try {
            this.result = node.work(this.input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        callback.complete(this.result);
    }

    public ReqResult getResult() {
        return result;
    }
}
