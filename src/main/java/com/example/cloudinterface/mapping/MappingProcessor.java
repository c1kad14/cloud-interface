package com.example.cloudinterface.mapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.example.cloudinterface.CloudApiClient;
import com.example.cloudinterface.mapping.parser.XmlParser;

public class MappingProcessor {
	
	private CloudApiClient apiClient;
	private String interfaceId;
	private File f;
	
	public MappingProcessor(String interfaceId, File f) {
		apiClient = new CloudApiClient();
		this.interfaceId = interfaceId;
		this.f = f;
	}
	
	public void process() {
		FileInputStream is = null;
		try {
			is = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		SAXParserFactory saxFactory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = saxFactory.newSAXParser();
			XmlParser parser = new XmlParser();
			saxParser.parse(is, parser);
			LinkedList<Node> nodes = parser.getNodes();
			
			NodeJsonCreator jsonCreator = new NodeJsonCreator();
			ArrayList<String> jsonList = new ArrayList<String>();
			for (Node node : nodes) {
				jsonList.add(jsonCreator.creteJson(node, interfaceId));
			}
			for (String json : jsonList) {
				apiClient.doPost("nodes", json);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
