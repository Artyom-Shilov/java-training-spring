package com.shilov.spring_reservation.controllers.requests;

public class MakeCurrentUserReservationRequest {

    private String spaceId;
    private ReservationDateTimeInput reservationDateTimeInput;

    public MakeCurrentUserReservationRequest(String spaceId, ReservationDateTimeInput reservationDateTimeInput) {
        this.spaceId = spaceId;
        this.reservationDateTimeInput = reservationDateTimeInput;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public ReservationDateTimeInput getReservationDateTimeInput() {
        return reservationDateTimeInput;
    }

    public void setReservationDateTimeInput(ReservationDateTimeInput reservationDateTimeInput) {
        this.reservationDateTimeInput = reservationDateTimeInput;
    }
}
