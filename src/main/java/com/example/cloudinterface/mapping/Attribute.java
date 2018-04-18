package com.example.cloudinterface.mapping;

import java.util.UUID;

public class Attribute {
	
	private String id;
    private String name;
    
    public Attribute(String attributeName) {
    	id = UUID.randomUUID().toString();
    	this.name = attributeName;
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
}
