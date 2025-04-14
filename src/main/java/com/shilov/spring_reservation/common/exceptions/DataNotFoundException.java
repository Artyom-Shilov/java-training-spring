package com.shilov.spring_reservation.common.exceptions;

public class DataNotFoundException extends ServiceException {

    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataNotFoundException(Throwable cause) {
        super(cause);
    }

    public DataNotFoundException(Class<?> clazz, Long id) {
        super(clazz.getSimpleName() + " with id " + id + " not found");
    }
}
