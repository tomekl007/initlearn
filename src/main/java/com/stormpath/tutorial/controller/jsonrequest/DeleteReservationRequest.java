package com.stormpath.tutorial.controller.jsonrequest;

/**
 * Created by tomasz.lelek on 23/01/16.
 */
public class DeleteReservationRequest {
    public Long getFromHour() {
        return fromHour;
    }

    public void setFromHour(Long fromHour) {
        this.fromHour = fromHour;
    }

    public Long fromHour;
}
