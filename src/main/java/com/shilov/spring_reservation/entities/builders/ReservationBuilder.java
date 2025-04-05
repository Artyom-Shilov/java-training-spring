package com.shilov.spring_reservation.entities.builders;

import com.shilov.spring_reservation.common.enums.ReservationStatus;
import com.shilov.spring_reservation.entities.Reservation;
import com.shilov.spring_reservation.entities.ReservationDateTime;
import com.shilov.spring_reservation.entities.Space;
import com.shilov.spring_reservation.entities.User;

public class ReservationBuilder {
    private User customer;
    private Space space;
    private ReservationStatus status;
    private ReservationDateTime reservationDateTime;
    private Long id;

    public ReservationBuilder setCustomer(User customer) {
        this.customer = customer;
        return this;
    }

    public ReservationBuilder setSpace(Space space) {
        this.space = space;
        return this;
    }

    public ReservationBuilder setStatus(ReservationStatus status) {
        this.status = status;
        return this;
    }

    public ReservationBuilder setReservationDateTime(ReservationDateTime reservationDateTime) {
        this.reservationDateTime = reservationDateTime;
        return this;
    }

    public ReservationBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public Reservation createReservation() {
        return new Reservation(id, customer, space, status, reservationDateTime);
    }
}