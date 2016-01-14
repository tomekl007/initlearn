package com.stormpath.tutorial.controller.jsonrequest;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.IOException;

/**
 * Created by tomasz.lelek on 13/01/16.
 */
public class JodaDateTimeJsonSerializer extends JsonSerializer<DateTime> {

    private static final String dateFormat = ("dd/MM/yyyy-hh:mm:ss");

    @Override
    public void serialize(DateTime date, JsonGenerator json, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        String formattedDate = DateTimeFormat.forPattern(dateFormat).print(date);
        json.writeString(formattedDate);
    }
}