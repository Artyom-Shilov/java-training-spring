package com.shilov.spring_reservation.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

public class SpaceInput {

    @NotBlank
    @Pattern(regexp = "\\b(open|private|room)\\b")
    private String type;

    @PositiveOrZero
    private int price;

    public @NotBlank String getType() {
        return type;
    }

    public void setType(@NotBlank String type) {
        this.type = type;
    }

    @PositiveOrZero
    public int getPrice() {
        return price;
    }

    public void setPrice(@PositiveOrZero int price) {
        this.price = price;
    }
}
