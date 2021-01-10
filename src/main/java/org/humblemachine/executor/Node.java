package org.humblemachine.executor;


import org.humblemachine.models.ReqResult;

public interface Node {
    public ReqResult work(ReqResult request) throws Exception;
}
