package com.example.cloudinterface.mapping.parser;

import java.util.LinkedList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.example.cloudinterface.mapping.Attribute;
import com.example.cloudinterface.mapping.Node;

public class XmlParser extends DefaultHandler {
	
private LinkedList<Node> nodeList;
private String parentId;
	
	public XmlParser() {
		nodeList = new LinkedList<Node>();
		parentId = null;
	}
	
	public LinkedList<Node> getNodes() {
		return nodeList;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) 
			throws SAXException {
		Node node = new Node(qName);
		if (attributes != null && attributes.getLength() > 0) {
			for (int i = 0; i < attributes.getLength(); i++) {
				Attribute attribute = new Attribute(attributes.getQName(i));
				node.getAttributes().add(attribute);
			}
		}
		
		node.setParentId(parentId);
		parentId = node.getId();
		
//		if (nodeList.peekLast() != null) {
//			node.setParentId(nodeList.peekLast().getId());
//		}
//		else {
//			node.setParentId(null);
//		}
		
		nodeList.addLast(node);
	}
	
	@Override
	public void endElement (String uri, String localName, String qName)
	        throws SAXException {
		if (nodeList.size() > 1) {
			parentId = nodeList.get(nodeList.size() - 2).getId();
		}
	}

}
