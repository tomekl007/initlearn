package com.stormpath.tutorial.controller.jsonrequest;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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

    public String getFromHour() {
        return fromHour;
    }

    public void setFromHour(String fromHour) {
        this.fromHour = fromHour;
    }

    public String fromHour;
}
