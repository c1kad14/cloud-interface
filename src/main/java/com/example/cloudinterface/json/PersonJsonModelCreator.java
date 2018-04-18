package com.example.cloudinterface.json;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;

public class PersonJsonModelCreator implements JsonModelCreator {
	
	public JsonObject createJsonObject() {
		JsonObject personModel = Json.createObjectBuilder()
				.add("first", JsonValue.NULL)
				.add("last", JsonValue.NULL)
				.add("middle", JsonValue.NULL)
				.add("birthDate", JsonValue.NULL)
				.add("sex", JsonValue.NULL)
				.build();
		return personModel;
	}
}
