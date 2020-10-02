package com.takeaway.challenge.util;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Helper class to serialize/deserialize POJOs/Maps to JSON and vice versa
 * Created by Ankush on 17/07/17.
 */
public class Json {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static{
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static <T> T toObject(final String json, final Class<T> clazz) throws Exception {
        Assert.notNull(clazz, "clazz cannot be null");
        return objectMapper
                .readValue(json, clazz);
    }

    /**
     * Serialize a POJO/Map to JSON String
     * @param object The Object to convert to a JSON string
     * @return The JSON representation of @param object
     * @throws Exception
     */
    public static String encode(final Object object) throws Exception {
        Assert.notNull(object, "object cannot be null");
        return objectMapper
                .writeValueAsString(object);
    }



}
