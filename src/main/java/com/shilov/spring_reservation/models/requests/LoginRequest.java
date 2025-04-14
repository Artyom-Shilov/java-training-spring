package com.shilov.spring_reservation.models.requests;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank String login,
        @NotBlank String password) {}
