package org.humblemachine.workflow;


public class TaskIdentifier {
    private String id;

    public TaskIdentifier(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof TaskIdentifier) {
            return ((TaskIdentifier)other).hashCode() == this.hashCode();
        }
        return false;
    }
}
