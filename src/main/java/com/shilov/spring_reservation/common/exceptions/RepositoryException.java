package com.shilov.spring_reservation.common.exceptions;

public class RepositoryException extends Exception {

    public RepositoryException(String errorMessage) {
        super(errorMessage);
    }

    public RepositoryException(Throwable cause) {
        super(cause);
    }

    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
