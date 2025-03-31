package com.shilov.spring_reservation.controllers;

import com.shilov.spring_reservation.controllers.responses.Response;

public interface AuthController {

    Response loginAsAdmin(String login);

    Response loginAsCustomer(String login);

    Response logout();
}
