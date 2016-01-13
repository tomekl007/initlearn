package com.stormpath.tutorial.controller.jsonrequest;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.DateTime;

/**
 * Created by tomasz.lelek on 13/01/16.
 */

public class ReservationRequest {
    public String teacher;

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public DateTime getFromHour() {
        return fromHour;
    }

    public void setFromHour(DateTime fromHour) {
        this.fromHour = fromHour;
    }

    @JsonSerialize(using=JodaDateTimeJsonSerializer.class)
    public DateTime fromHour;
}
