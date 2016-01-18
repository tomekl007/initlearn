package com.stormpath.tutorial.controller.jsonrequest;

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

    public Long getFromHour() {
        return fromHour;
    }

    public void setFromHour(Long fromHour) {
        this.fromHour = fromHour;
    }

    public Long fromHour;
}
