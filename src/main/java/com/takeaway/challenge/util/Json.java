package com.takeaway.challenge.util;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.takeaway.challenge.jackson.LocalDateDeserializer;
import com.takeaway.challenge.jackson.LocalDateSerializer;
import com.takeaway.challenge.jackson.ZonedDateTimeSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Optional;


public class Json {

    private static final Logger logger = LoggerFactory.getLogger(Json.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        var simpleModule = new SimpleModule();
        simpleModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        simpleModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        simpleModule.addSerializer(ZonedDateTime.class, new ZonedDateTimeSerializer());

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .registerModule(simpleModule);

    }

    public static <T> T toObject(final String json, final Class<T> clazz) throws Exception {
        Assert.notNull(clazz, "clazz cannot be null");
        return objectMapper
                .readValue(json, clazz);
    }

    public static String encode(final Object object)  {
        Assert.notNull(object, "object cannot be null");
        try {
            return objectMapper
                    .writeValueAsString(object);
        }
        catch (final JsonProcessingException ex) {
            logger.error("", ex);
            return null;
        }
    }



}
