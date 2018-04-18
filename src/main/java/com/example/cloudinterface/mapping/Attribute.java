package com.example.cloudinterface.mapping;

public class Attribute {
	
    private String name;
    
    public Attribute(String attributeName) {
    	this.name = attributeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
