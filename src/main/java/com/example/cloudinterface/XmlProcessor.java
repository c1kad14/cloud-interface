package com.example.cloudinterface;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.json.JsonObject;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.example.cloudinterface.json.JsonModelCreator;
import com.example.cloudinterface.json.PersonJsonModelCreator;
import com.example.cloudinterface.xml.parser.XmlParser;
import com.google.gson.Gson;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

public class XmlProcessor {
	
	private String type;
	private HashMap<String, String> mapping;
	
	public XmlProcessor(String type, HashMap<String, String> mapping) {
		this.type = type;
		this.mapping = mapping;
	}
	
	public String process(File f) {
		JsonModelCreator model;
		JsonObject blankJson = null;
		
		switch(type) {
			case "person" : 
				model = new PersonJsonModelCreator();
				break;
			default :
				model = null;
				break;
		}
		
		if (model != null) {
			blankJson = model.createJsonObject();
		}
		
		SAXParserFactory saxFactory = SAXParserFactory.newInstance();
		XmlParser parser = null;
		try {
			SAXParser saxParser = saxFactory.newSAXParser();
			parser = new XmlParser(mapping);
			saxParser.parse(f, parser);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		HashMap<String, String> jsonValues = parser.getJsonValues();
		
		String s = blankJson.toString();
		DocumentContext doc = JsonPath.parse(s);
		for (Entry<String, String> entry : jsonValues.entrySet()) {
			doc.set(entry.getKey(), entry.getValue());
		}
		String str = doc.read("$").toString();
		str = str.replaceAll("=", ":");
		return new Gson().toJson(str);
	}

}
