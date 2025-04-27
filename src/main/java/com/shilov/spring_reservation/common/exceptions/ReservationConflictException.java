package com.shilov.spring_reservation.common.exceptions;

public class ReservationConflictException extends ServiceException {

    public ReservationConflictException(String message) {
        super(message);
    }

    public ReservationConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReservationConflictException(Throwable cause) {
        super(cause);
    }
}
