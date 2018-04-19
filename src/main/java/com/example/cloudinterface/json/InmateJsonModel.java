package com.example.cloudinterface.json;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;

public class InmateJsonModel implements JsonModelCreator {

	public JsonObject createJsonObject() {
		JsonObject inmateModel = Json.createObjectBuilder()
				.add("agency", JsonValue.NULL)
				.add("person", Json.createObjectBuilder()
						.add("id", JsonValue.NULL)
						.add("first", JsonValue.NULL)
						.add("last", JsonValue.NULL)
						.add("middle", JsonValue.NULL)
						.add("birthDate", JsonValue.NULL)
						.add("sex", JsonValue.NULL))
				.add("assignedHousing", JsonValue.NULL)
				.add("currentBookingId", JsonValue.NULL)
				.build();
		return inmateModel;
	}

}
