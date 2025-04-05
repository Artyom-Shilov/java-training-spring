package com.shilov.spring_reservation.models;

import jakarta.validation.constraints.NotBlank;

public class LoginInput {

    @NotBlank
    private String login;

    public @NotBlank String getLogin() {
        return login;
    }

    public void setLogin(@NotBlank String login) {
        this.login = login;
    }
}
