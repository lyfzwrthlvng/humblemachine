package org.humblemachine.executor;

import KC.DbOperations;
import KC.entities.*;

import java.util.ArrayList;
import java.util.HashMap;

public class DbRead implements Node {
    @Override
    public boolean process(KCAccessRequest request) {
        KCQueryRequest readRequest = (KCQueryRequest) request;

        ArrayList<Knowledge> knowledges = new ArrayList<>();
        for(String keyword: readRequest.getKeywordList()) {
            KnowledgeTag tag = DbOperations.getKnowledgeTagByUserAndTag(keyword, readRequest.getUserId());
            knowledges.addAll(DbOperations.getKnowledge(tag));
        }
        NodeResult result = new NodeResult();
        HashMap<String, Object> map = new HashMap<>();
        map.put(getResultNameForNode(), knowledges);
        result.setResult(map);
        setOutput(result);
        return true;
    }

    @Override
    public boolean process() {
        return false;
    }

    @Override
    public NodeResult getOutput() {
        return output;
    }

    @Override
    public void setOutput(NodeResult result) {
        output.setResult(result.getResult());
    }

    @Override
    public String getResultNameForNode() {
        // Doesn't really need a result
        return "Knowledge";
    }
}
