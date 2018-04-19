package com.example.cloudinterface.json;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;

public class BookingJsonModel implements JsonModelCreator {

	public JsonObject createJsonObject() {
		JsonObject bookingModel = Json.createObjectBuilder()
				.add("inmate", Json.createObjectBuilder()
						.add("agency", JsonValue.NULL)
						.add("person", Json.createObjectBuilder()
								.add("first", JsonValue.NULL)
								.add("last", JsonValue.NULL)
								.add("middle", JsonValue.NULL)
								.add("birthDate", JsonValue.NULL)
								.add("sex", JsonValue.NULL))
						.add("assignedHousing", JsonValue.NULL)
						.add("currentBookingId", JsonValue.NULL))
				.add("bookingAgency", JsonValue.NULL)
				.add("bookingType", JsonValue.NULL)
				.build();
		return bookingModel;
	}

}
