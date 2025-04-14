package com.shilov.spring_reservation.controllers;

import com.shilov.spring_reservation.common.enums.UserRole;
import com.shilov.spring_reservation.common.exceptions.ServiceException;
import com.shilov.spring_reservation.models.UserModel;
import com.shilov.spring_reservation.models.requests.RegistrationRequest;
import com.shilov.spring_reservation.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AdminController {

    private final AuthService authService;

    @Autowired
    public AdminController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/admin")
    public ResponseEntity<UserModel> createAdmin(@RequestBody @Valid RegistrationRequest request) throws ServiceException {
        return ResponseEntity.ok(authService.register(request, UserRole.ROLE_ADMIN));
    }
}
