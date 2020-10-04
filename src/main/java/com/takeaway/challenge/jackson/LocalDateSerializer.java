package com.takeaway.challenge.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


//This class is for test cases only
public class LocalDateSerializer extends JsonSerializer<LocalDate> {

    @Override
    public void serialize(
            final LocalDate localDate,
            final JsonGenerator jsonGenerator,
            final SerializerProvider serializerProvider
    ) throws IOException
    {
        jsonGenerator.writeString(
                DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDate)
        );
    }
}
