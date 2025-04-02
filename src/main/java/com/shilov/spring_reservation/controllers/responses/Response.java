package com.shilov.spring_reservation.controllers.responses;

import com.shilov.spring_reservation.common.enums.ResponseStatus;

public class Response {

    private ResponseStatus status;
    private String payload;

    public Response(ResponseStatus status, String payload) {
        this.status = status;
        this.payload = payload;
    }

    public Response(ResponseStatus status) {
        this.status = status;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
