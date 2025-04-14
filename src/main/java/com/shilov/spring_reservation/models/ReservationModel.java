package com.shilov.spring_reservation.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationModel(
        Long id,
        @NotNull Long spaceId,
        @NotNull Long customerId,
        @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
        @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
        @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime,
        @Pattern(regexp = "\\b(ACTIVE|INACTIVE|CANCELLED)\\b", message = "Unknown reservation status") String status) {}
