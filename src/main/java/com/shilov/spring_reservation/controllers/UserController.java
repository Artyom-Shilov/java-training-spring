package com.shilov.spring_reservation.controllers;

import com.shilov.spring_reservation.common.exceptions.ServiceException;
import com.shilov.spring_reservation.models.ReservationModel;
import com.shilov.spring_reservation.models.UserModel;
import com.shilov.spring_reservation.services.ReservationService;
import com.shilov.spring_reservation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    private final ReservationService reservationService;

    @Autowired
    public UserController(UserService userService, ReservationService reservationService) {
        this.userService = userService;
        this.reservationService = reservationService;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserModel> getUser(@PathVariable("id") Long id) throws ServiceException {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("/users/{userId}/reservations")
    public ResponseEntity<List<ReservationModel>> getUserReservations(@PathVariable("userId") Long userId) throws ServiceException {
        return ResponseEntity.ok(reservationService.getUserReservations(userId));
    }
}
