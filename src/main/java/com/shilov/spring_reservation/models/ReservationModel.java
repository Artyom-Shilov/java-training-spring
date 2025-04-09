package com.shilov.spring_reservation.models;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationModel(
        @NotNull Long id,
        @NotNull Long spaceId,
        @NotNull Long customerId,
        @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
        @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
        @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime) {}
