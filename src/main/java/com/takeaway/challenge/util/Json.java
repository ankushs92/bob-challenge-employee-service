package com.takeaway.challenge.util;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Json {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static <T> T toObject(final String json, final Class<T> clazz) throws Exception {
        Assert.notNull(clazz, "clazz cannot be null");
        return objectMapper
                .readValue(json, clazz);
    }

    public static String encode(final Object object) throws Exception {
        Assert.notNull(object, "object cannot be null");
        return objectMapper
                .writeValueAsString(object);
    }



}
