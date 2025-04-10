package com.shilov.spring_reservation.controllers;

import com.shilov.spring_reservation.common.exceptions.ServiceException;
import com.shilov.spring_reservation.models.ReservationDateTimeModel;
import com.shilov.spring_reservation.models.SpaceModel;
import com.shilov.spring_reservation.services.SpaceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SpaceController {

    private final SpaceService spaceService;

    @Autowired
    public SpaceController(SpaceService spaceService) {
        this.spaceService = spaceService;
    }

    @PostMapping("/space")
    public ResponseEntity<Long> createSpace(@Valid @RequestBody SpaceModel spaceModel) throws ServiceException {
        return ResponseEntity.ok(spaceService.addNewSpace(spaceModel));
    }

    @DeleteMapping("/space/{id}")
    public ResponseEntity<String> deleteSpace(@PathVariable("id") Long id) throws ServiceException {
        spaceService.deleteSpace(id);
        return ResponseEntity.ok("space with id " + id + " has been deleted");
    }

    @PostMapping("space/{id}")
    public ResponseEntity<SpaceModel> updateSpace(@PathVariable("id") Long id, @RequestBody @Valid SpaceModel spaceModel) throws ServiceException {
        return ResponseEntity.ok(spaceService.updateSpace(id, spaceModel));
    }

    @GetMapping("space/available")
    public ResponseEntity<List<SpaceModel>> getAvailableSpaces(@RequestBody @Valid ReservationDateTimeModel reservationDateTime) throws ServiceException {
       return ResponseEntity.ok(spaceService.getAvailableForReservationSpaces(reservationDateTime));
    }
}
