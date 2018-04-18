package com.example.cloudinterface.xml.parser;

import java.util.HashMap;
import java.util.Map.Entry;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlParser extends DefaultHandler {
	
	private String xPath;
	private String tagValue;
	private HashMap<String, String> mapping;
	private HashMap<String, String> jsonValues;
	
	public XmlParser(HashMap<String, String> mapping) {
		xPath = "";
		tagValue = "";
		this.mapping = mapping;
		this.jsonValues = new HashMap<String, String>();
	}
	
	public HashMap<String, String> getJsonValues() {
		return jsonValues;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) 
			throws SAXException {
		xPath += "/" + qName;
		
	}
	
	@Override
	public void characters(char ch[], int start, int length)
	        throws SAXException {
		for (int i = start; i < start + length; ++i) {
			if (ch[i] == '\n' || ch[i] == '\t') {
				return;
			}
			else {
				tagValue += ch[i];
			}
		}
		checkElement();
	}
	
	@Override
	public void endElement (String uri, String localName, String qName)
	        throws SAXException {
		xPath = xPath.substring(0, xPath.length() - (qName.length() + 1));
		tagValue = "";
	}
	
	private void checkElement() {
		for (Entry<String, String> entry : mapping.entrySet()) {
			if (entry.getValue().equals(xPath)) {
				jsonValues.put(entry.getKey(), tagValue);
			}
		}
	}
}
