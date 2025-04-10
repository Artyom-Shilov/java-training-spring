package com.shilov.spring_reservation.controllers;

import com.shilov.spring_reservation.common.exceptions.ServiceException;
import com.shilov.spring_reservation.models.UserModel;
import com.shilov.spring_reservation.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserModel> getUser(@PathVariable("id") Long id) throws ServiceException {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PostMapping("/user")
    public ResponseEntity<Long> createUser(@RequestBody @Valid UserModel user) throws ServiceException {
        return ResponseEntity.ok(userService.addUser(user));
    }
}
