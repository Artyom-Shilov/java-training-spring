package com.shilov.spring_reservation.common.exceptions;

public class ReservationTimeConflict extends ServiceException {

    public ReservationTimeConflict(String message) {
        super(message);
    }

    public ReservationTimeConflict(String message, Throwable cause) {
        super(message, cause);
    }

    public ReservationTimeConflict(Throwable cause) {
        super(cause);
    }
}
