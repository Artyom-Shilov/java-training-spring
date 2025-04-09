package com.shilov.spring_reservation.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

public record SpaceModel(
        @NotNull Long id,
        @NotBlank
        @Pattern(regexp = "\\b(OPEN|PRIVATE|ROOM)\\b", message = "Unknown space type") String type,
        @PositiveOrZero int price) {}
