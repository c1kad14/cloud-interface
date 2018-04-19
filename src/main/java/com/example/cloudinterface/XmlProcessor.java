package com.example.cloudinterface;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.json.JsonObject;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import com.example.cloudinterface.json.BookingJsonModel;
import com.example.cloudinterface.json.InmateJsonModel;
import com.example.cloudinterface.json.JsonModelCreator;
import com.example.cloudinterface.json.PersonJsonModel;
import com.example.cloudinterface.xml.parser.XmlParser;
import com.google.gson.Gson;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

public class XmlProcessor {
	
	private String interfaceId;
	private CloudApiClient apiClient;
	
	public XmlProcessor(String interfaceId) {
		this.interfaceId = interfaceId;
		apiClient = new CloudApiClient();
	}
	
	private String getType() {
		String interfacesTable = apiClient.doGet("interfaces");
		JSONArray jsonArray = new JSONArray(interfacesTable);
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			
			if (jsonObject.get("name") != null && jsonObject.get("name").toString().equals(interfaceId)) {
				return jsonObject.getString("type");
			}
		}
		return null;
	}
	
	private HashMap<String, String> getMapping() {
		String mappingsTable = apiClient.doGet("mappings");
		JSONArray jsonArray = new JSONArray(mappingsTable);
		HashMap<String, String> mapping = new HashMap<String, String>();
		for (int i = 0; i < jsonArray.length(); i++) {
		    JSONObject jsonObject = jsonArray.getJSONObject(i);
		    if (jsonObject.getString("interfaceId").equals(interfaceId)) {
		    	String jsonPath = jsonObject.getString("local");
		    	String xmlPath = jsonObject.getString("remote");
		    	mapping.put(jsonPath, xmlPath);
		    }
		}
		return mapping;
	}
	
	public String process(File f) {
		JsonModelCreator model;
		JsonObject blankJson = null;
		
		String type = getType();
		switch(type) {
			case "person" : 
				model = new PersonJsonModel();
				break;
			case "inmate" :
				model = new InmateJsonModel();
				break;
			case "booking" :
				model = new BookingJsonModel();
				break;
			default :
				model = null;
				break;
		}
		
		HashMap<String, String> mapping = getMapping();
		
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
		
		DocumentContext doc = JsonPath.parse(blankJson.toString());
		for (Entry<String, String> entry : jsonValues.entrySet()) {
			doc.set(entry.getKey(), "\"" + entry.getValue() + "\"");
		}
		String str = doc.read("$").toString();
		str = str.replaceAll("=", ":");
		try {
			apiClient.doPost("persons", str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Gson().toJson(str);
	}

}
