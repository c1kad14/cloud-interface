package com.example.cloudinterface.mapping;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

public class NodeJsonCreator {
	
	public String creteJson(Node node, String interfaceId) {
		JsonObjectBuilder nodeJsonBuilder = Json.createObjectBuilder()
		.add("id", node.getId())
		.add("name", node.getName())
		.add("interfaceId", interfaceId)
		.add("parentId", (node.getParentId() != null ? node.getParentId() : JsonValue.NULL.toString()));
		
		JsonArrayBuilder attrArrayBuilder = Json.createArrayBuilder();
		
		for (Attribute attribute : node.getAttributes()) {
			JsonObject attributeJson = Json.createObjectBuilder()
					.add("name", attribute.getName())
					.build();
			attrArrayBuilder.add(attributeJson);
		}
		
		nodeJsonBuilder.add("attributes", attrArrayBuilder);
		
		JsonObject nodeJson = nodeJsonBuilder.build();
		return nodeJson.toString();
	}

}
