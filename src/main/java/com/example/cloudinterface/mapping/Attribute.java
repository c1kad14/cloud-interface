package com.example.cloudinterface.mapping;

import java.util.UUID;

public class Attribute {
	
	private String id;
    private String name;
    private String nodeId;
    
    public Attribute(String attributeName, String nodeId) {
    	id = UUID.randomUUID().toString();
    	this.name = attributeName;
    	this.nodeId = nodeId;
    }
    
    public String getId() {
    	return id;
    }
    
    public String getNodeId() {
    	return nodeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
