package com.shilov.spring_reservation.models;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

public record SpaceModel(
        Long id,
        @Pattern(regexp = "\\b(OPEN|PRIVATE|ROOM)\\b", message = "Unknown space type") String type,
        @PositiveOrZero(message = "Should be positive or zero") int price) {
}
