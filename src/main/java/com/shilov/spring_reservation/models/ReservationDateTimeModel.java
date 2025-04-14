package com.shilov.spring_reservation.models;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDateTimeModel(
        @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
        @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
        @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime) {

    public ReservationDateTimeModel(ReservationModel reservationModel) {
        this(reservationModel.date(), reservationModel.startTime(), reservationModel.endTime());
    }

}
