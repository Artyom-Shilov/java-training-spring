package com.shilov.spring_reservation.controllers;

import com.shilov.spring_reservation.common.exceptions.ServiceException;
import com.shilov.spring_reservation.models.ReservationDateTimeModel;
import com.shilov.spring_reservation.models.ReservationModel;
import com.shilov.spring_reservation.services.ReservationService;
import com.shilov.spring_reservation.services.SpaceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationController {

    private final ReservationService reservationService;
    private final SpaceService spaceService;

    @Autowired
    public ReservationController(ReservationService reservationService, SpaceService spaceService) {
        this.reservationService = reservationService;
        this.spaceService = spaceService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationModel>> getAllReservations() throws ServiceException {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Long> makeReservation(@RequestBody @Valid ReservationModel reservationModel) throws ServiceException {
        spaceService.checkSpaceReservationAvailability(
                reservationModel.spaceId(), new ReservationDateTimeModel(reservationModel));
        return ResponseEntity.ok(reservationService.makeReservation(reservationModel));
    }

    @PutMapping("/reservations/{id}")
    public ResponseEntity<String> updateReservation(@PathVariable("id") Long id, @RequestBody @Valid  ReservationModel reservationModel) throws ServiceException {
        reservationService.updateReservation(id, reservationModel);
        return ResponseEntity.ok("Reservation with id : " + id + " has been updated");
    }
}
