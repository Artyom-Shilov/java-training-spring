package com.shilov.spring_reservation.models;

import jakarta.validation.constraints.NotBlank;

public record ErrorModel(@NotBlank String message) {}
