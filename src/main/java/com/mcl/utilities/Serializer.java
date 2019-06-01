package com.mcl.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcl.models.Item;
import com.mcl.models.ItemListingForSerialization;

import java.util.List;

public class Serializer {

    private ObjectMapper mapper;

    public Serializer() {
        mapper = new ObjectMapper();
    }

    public String buildJsonResponse(List<Item> items) throws JsonProcessingException {
        String data = serializeItemListingArrayToJson(items);
        return String.format("{\"data\":%s}", data);
    }

    public String buildEmptyJsonResponse() {
        return "{\"data\":{}}";
    }

    private String serializeItemListingArrayToJson(List<Item> items) throws JsonProcessingException {
        StringBuilder builder = new StringBuilder("{");
        int num = 0;
        for (int i = 0; i < items.size(); i++) {
            builder.append(String.format("\"item%03d\":", num));
            builder.append(serializeObjToJson(new ItemListingForSerialization(items.get(i))));
            if (i == items.size() - 1) {
                builder.append("}");
            } else {
                builder.append(",");
            }
            num++;
        }
        return builder.toString();
    }

    public String serializeObjToJson(ItemListingForSerialization item) throws JsonProcessingException {
        return mapper.writeValueAsString(item);
    }

}

