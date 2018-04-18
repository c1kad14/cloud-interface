package com.example.cloudinterface.mapping;

import java.util.LinkedList;
import java.util.UUID;

public class Node {
	private String id;
    private String name;
    private String parentId;
    private LinkedList<Attribute> attributes;
    
    public Node(String name) {
    	id = UUID.randomUUID().toString();
    	this.name = name;
    	attributes = new LinkedList<Attribute>();
    }
    
    public String getId() {
    	return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<Attribute> getAttributes() {
        return attributes;
    }

    public boolean hasAttributes() {
        return attributes.size() > 0;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
