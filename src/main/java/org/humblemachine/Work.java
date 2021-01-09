package org.humblemachine;

import org.humblemachine.models.NodeResult;
import org.humblemachine.models.Request;
import org.humblemachine.models.ResultContainer;

import java.util.*;

/*
* Work is composed of one or more Executions
* */
public class Work {

    private List<Execution> executionSet;
    private Execution closingExecution;

    public Work(){
        this.executionSet = new LinkedList<>();
    }

    public Work(List<Execution> executions){
        this();
        executionSet.addAll(executions);

    }

    public NodeResult start(Request request, ResultContainer container) {
        executionSet.get(0).update(null, request);

    }

    public static class WorkBuilder {
        private List<Execution> executionSet;
        private final Execution lastExecution = new Execution(null, null);
        //TODO a special last execution that listens to all the other executions and just returns the
        //

        public WorkBuilder(){
            this.executionSet = new LinkedList<>();
        }

        public WorkBuilder addExecution(Execution execution) {
            executionSet.add(execution);
            return this;
        }

        public WorkBuilder firstExecution(Execution execution) throws Exception {
            if(execution.getTriggerArgs().keySet().size() > 0) {
                throw new Exception("invalid first execution");
            }
            this.executionSet.add(0, execution);
            return this;
        }

        public Work build() throws Exception{
            return new Work(executionSet);
        }

    }
}
