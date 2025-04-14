package com.shilov.spring_reservation.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserModel(
        Long id,
        @NotBlank String login,
        @Pattern(regexp = "\\b(ADMIN|CUSTOMER)\\b", message = "Unknown user role") String role) {}
