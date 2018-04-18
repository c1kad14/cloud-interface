package com.example.cloudinterface.json;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;

public class PersonJsonModelCreator implements JsonModelCreator {
	
	public JsonObject createJsonObject() {
		JsonObject personModel = Json.createObjectBuilder()
				.add("a", Json.createObjectBuilder()
						.add("b", Json.createObjectBuilder()
								.add("c", JsonValue.NULL)
								.add("d", JsonValue.NULL))
						.add("e", JsonValue.NULL)
						.add("f", JsonValue.NULL))
				
				.build();
		return personModel;
//		JsonObject personModel = Json.createObjectBuilder()
//				.add("firstName", JsonValue.NULL)
//				.add("lastName", JsonValue.NULL)
//				.add("middleName", JsonValue.NULL)
//				.add("dob", JsonValue.NULL)
//				.add("sex", JsonValue.NULL)
//				.build();
//		return personModel;
	}

}
