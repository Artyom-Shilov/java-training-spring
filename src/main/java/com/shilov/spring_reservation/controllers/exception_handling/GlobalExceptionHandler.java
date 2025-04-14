package com.shilov.spring_reservation.controllers.exception_handling;

import com.shilov.spring_reservation.common.exceptions.DataNotFoundException;
import com.shilov.spring_reservation.common.exceptions.EntityAlreadyExistsException;
import com.shilov.spring_reservation.common.exceptions.ReservationConflictException;
import com.shilov.spring_reservation.models.ErrorModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorModel> handleDataNotFoundException(DataNotFoundException exception) {
        logger.info(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorModel(exception.getMessage()));
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ErrorModel> handleEntityAlreadyExistsException(EntityAlreadyExistsException exception) {
        logger.info(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorModel(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorModel> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        logger.info(exception.getMessage(), exception);
        Object[] validationDetails = exception.getDetailMessageArguments();
        String validationMessage = validationDetails != null
                ? Arrays.stream(validationDetails).map(Object::toString).filter(m -> !m.isBlank()).collect(Collectors.joining())
                : "Validation Failed";
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorModel(validationMessage));
    }

    @ExceptionHandler(ReservationConflictException.class)
    public ResponseEntity<ErrorModel> handleReservationConflictExceptionException(ReservationConflictException exception) {
        logger.info(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorModel(exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorModel> handleException(Exception exception) {
        logger.info(exception.getMessage(), exception);
        return ResponseEntity
                .status(500)
                .body(new ErrorModel("Unexpected error"));
    }
}
