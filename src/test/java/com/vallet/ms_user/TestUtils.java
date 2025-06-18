package com.vallet.ms_user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class TestUtils {

    @Autowired
    private ObjectMapper objectMapper;

    public <T> T convertStringToObject(String json, Class<T> target) throws IOException {
        return objectMapper.readValue(json, target);
    }

    public <T> List<T> convertJsonToObjectList(String json, Class<T> className) throws IOException {
        CollectionLikeType type = objectMapper.getTypeFactory().constructCollectionLikeType(List.class, className);
        return objectMapper.readValue(json, type);
    }

    public <T> String convertObjectToJson(T object) throws IOException {
        return objectMapper.writeValueAsString(object);
    }

    public <T> String convertObjectListToJson(List<T> objectList) throws IOException {
        return objectMapper.writeValueAsString(objectList);
    }

}
