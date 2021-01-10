package org.humblemachine;

import org.humblemachine.executor.Callback;
import org.humblemachine.executor.Node;
import org.humblemachine.models.ReqResult;
import org.humblemachine.models.ReqResultImpl;
import org.humblemachine.workflow.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MachineTest {

    @Test
    public void testMachineBasic() throws Exception {

        ExecutorService es = Executors.newFixedThreadPool(2);

        WorkFlow wf = new WorkFlowBuilder()
                .addTask(new Task(new TaskIdentifier("authorize"), es, new Authorize()))
                .addTask(new Task(new TaskIdentifier("read"), es, new Read()))
                .addTask(new Task(new TaskIdentifier("formatA"), es, new FormatA()))
                .addTask(new Task(new TaskIdentifier("formatB"), es, new FormatB()))
                .addTask(new Task(new TaskIdentifier("close"), es, new Close()))
                .addDependency(new Dependency(new TaskIdentifier("authorize"), new TaskIdentifier("read")))
                .addDependency(new Dependency(new TaskIdentifier("read"), new TaskIdentifier("formatA")))
                .addDependency(new Dependency(new TaskIdentifier("read"), new TaskIdentifier("formatB")))
                .addDependency(new Dependency(new TaskIdentifier("formatA"), new TaskIdentifier("close")))
                .addDependency(new Dependency(new TaskIdentifier("formatB"), new TaskIdentifier("close")))
                .withCallback(result -> {
                    Assertions.assertEquals("collecting everything here",
                            (String)result.getValue(new TaskIdentifier("close"), "close"));
                })
                .build();
        ReqResult input = new ReqResultImpl("username", "user1");
        ((ReqResultImpl) input).getArgs().put("arg1","value1");
        wf.start(input);
        System.out.println("orchestrating from " + Thread.currentThread().getId());
    }

    class Authorize implements Node {
        @Override
        public ReqResult work(ReqResult request) throws Exception {
            String user = (String) request.getValue("username");
            if(!user.equals("user1")) {
                throw new Exception("authorization failed");
            }
            System.out.println("task " + "authorize " + Thread.currentThread().getId());
            return new ReqResultImpl("authorization", true);
        }
    }

    class Read implements Node {
        @Override
        public ReqResult work(ReqResult request) throws Exception {
            System.out.println("task " + "read " + Thread.currentThread().getId());
            return new ReqResultImpl("read", "i have read something");
        }
    }

    class FormatA implements Node {
        @Override
        public ReqResult work(ReqResult request) throws Exception {
            System.out.println("task " + "formatA " + Thread.currentThread().getId());
            return new ReqResultImpl("formatA", "i have read something and formatted it");
        }
    }

    class FormatB implements Node {
        @Override
        public ReqResult work(ReqResult request) throws Exception {
            System.out.println("task " + "formatB " + Thread.currentThread().getId());
            return new ReqResultImpl("formatB", "i have read something and formatted it like B");
        }
    }

    class Close implements Node {
        @Override
        public ReqResult work(ReqResult request) throws Exception {
            System.out.println("task " + "close! " + Thread.currentThread().getId());
            return new ReqResultImpl("close", "collecting everything here");
        }
    }
}
