package org.humblemachine.executor;

import org.humblemachine.models.ReqResult;

public interface Callback {
    public void complete(ReqResult result);
}
