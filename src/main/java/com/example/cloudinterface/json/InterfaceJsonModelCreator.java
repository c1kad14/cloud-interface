package com.example.cloudinterface.json;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;

public class InterfaceJsonModelCreator implements JsonModelCreator {
    public JsonObject createJsonObject() {
        JsonObject interfaceModel = Json.createObjectBuilder()
                .add("name", JsonValue.NULL)
                .build();
        return interfaceModel;
    }
}
