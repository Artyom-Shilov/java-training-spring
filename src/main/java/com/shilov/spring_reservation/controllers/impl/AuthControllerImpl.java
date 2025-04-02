package com.shilov.spring_reservation.controllers.impl;

import com.shilov.spring_reservation.common.enums.ResponseStatus;
import com.shilov.spring_reservation.common.enums.UserRole;
import com.shilov.spring_reservation.common.exceptions.ServiceException;
import com.shilov.spring_reservation.controllers.AuthController;
import com.shilov.spring_reservation.controllers.responses.Response;
import com.shilov.spring_reservation.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Autowired
    public AuthControllerImpl(AuthService authService) {
        this.authService = authService;
    }

    public Response loginAsAdmin(String login) {
        String successMessage = "Admin login operation success";
        Response response;
        try {
            authService.signIn(login, UserRole.ADMIN);
            response = new Response(ResponseStatus.SUCCESS, successMessage);
        } catch (ServiceException e) {
            response = new Response(ResponseStatus.FAILURE, e.getMessage());
        }
        return response;
    }

    public Response loginAsCustomer(String login) {
        String successMessage = "Customer login operation success";
        Response response;
        try {
            authService.signIn(login, UserRole.CUSTOMER);
            response = new Response(ResponseStatus.SUCCESS, successMessage);
        } catch (ServiceException e) {
            response = new Response(ResponseStatus.FAILURE, e.getMessage());
        }
        return response;
    }

    public Response logout() {
        String successMessage = "Logout operation success";
        Response response;
        try {
            authService.signOut();
            response = new Response(ResponseStatus.SUCCESS, successMessage);
        } catch (ServiceException e) {
            response = new Response(ResponseStatus.FAILURE, e.getMessage());
        }
        return response;
    }
}
