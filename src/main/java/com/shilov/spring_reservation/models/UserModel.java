package com.shilov.spring_reservation.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserModel(
        @NotNull Long id,
        @NotNull String login,
        @NotNull @Pattern(regexp = "\\b(ADMIN|CUSTOMER)\\b", message = "Unknown user role") String role) {}
