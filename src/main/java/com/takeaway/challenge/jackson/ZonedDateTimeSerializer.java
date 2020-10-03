package com.takeaway.challenge.jackson;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ZonedDateTimeSerializer  extends JsonSerializer<ZonedDateTime> {

    private static final Logger logger = LoggerFactory.getLogger(ZonedDateTimeSerializer.class);

    @Override
    public void serialize(ZonedDateTime timestamp, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if(Objects.nonNull(timestamp)) {
            try {
                gen.writeString(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS").format(timestamp));
            }
            catch(final Exception ex) {
                logger.error("", ex);
            }
        }
    }

}
