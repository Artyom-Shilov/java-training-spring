package com.shilov.spring_reservation.models;

import jakarta.validation.constraints.NotNull;

public class IdInput {

    @NotNull
    Long id;

    public @NotNull Long getId() {
        return id;
    }

    public void setId(@NotNull Long id) {
        this.id = id;
    }
}
