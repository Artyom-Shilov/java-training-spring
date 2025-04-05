package com.shilov.spring_reservation.controllers.impl;

import com.shilov.spring_reservation.common.exceptions.ServiceException;
import com.shilov.spring_reservation.entities.Reservation;
import com.shilov.spring_reservation.entities.ReservationDateTime;
import com.shilov.spring_reservation.models.IdInput;
import com.shilov.spring_reservation.models.ReservationInput;
import com.shilov.spring_reservation.services.ReservationService;
import com.shilov.spring_reservation.services.AuthService;
import com.shilov.spring_reservation.services.SpaceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ReservationControllerImpl {

    private final ReservationService reservationService;
    private final SpaceService spaceService;
    private final AuthService authService;

    @Autowired
    public ReservationControllerImpl(ReservationService reservationService, SpaceService spaceService,
                                     AuthService authService) {
        this.reservationService = reservationService;
        this.spaceService = spaceService;
        this.authService = authService;
    }

    @GetMapping("reservation/all")
    public String getAllReservations(Model model) throws ServiceException {
        List<Reservation> allReservations = reservationService.getAllReservations();
        model.addAttribute("reservations", allReservations);
        return "reservations_list";
    }

    @GetMapping("reservation/creation")
    public String showReservationCreationForm(Model model) {
        model.addAttribute(new ReservationInput());
        return "reservation_creation_form";
    }

    @PostMapping("reservation")
    public String makeReservation(@Valid @ModelAttribute ReservationInput reservationInput,
                                  BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "reservation_creation_form";
        }
        try {
            ReservationDateTime reservationDateTime = new ReservationDateTime(
                    reservationInput.getDate(),
                    reservationInput.getStartTime(),
                    reservationInput.getEndTime());
            Long spaceId = reservationInput.getSpaceId();
            if (!spaceService.checkSpaceReservationAvailability(spaceId, reservationDateTime)) {
                model.addAttribute("error", "Space not available for reservation");
                return "reservation_creation_form";
            }
            Long reservationId = reservationService.makeReservation(spaceService.getSpaceById(spaceId),
                    authService.getCurrentUser(), reservationDateTime);
            model.addAttribute("successMessage", "Reservation has been created with id : " + reservationId);
        } catch (ServiceException e) {
            model.addAttribute("error", "Error during reservation process");
        }
        return "reservation_creation_form";
    }

    @GetMapping("reservation/mine")
    public String getMyReservations(Model model) throws ServiceException {
        List<Reservation> userReservations = reservationService.getUserReservations(authService.getCurrentUser());
        model.addAttribute("reservations", userReservations);
        return "reservations_list";
    }

    @GetMapping("reservation/cancel")
    public String showCancelReservationForm(Model model) {
        model.addAttribute(new IdInput());
        return "reservation_cancel_form";
    }

    @PostMapping("reservation/cancel")
    public String cancelReservation(@Valid @ModelAttribute IdInput idInput,
                                    BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "reservation_cancel_form";
        }
        try {
            reservationService.cancelReservation(
                    idInput.getId(),
                    authService.getCurrentUser());
            model.addAttribute("successMessage", "Reservation was cancelled");
        } catch (ServiceException e) {
            model.addAttribute("error", "Error during reservation cancel process");
        }
        return "reservation_cancel_form";
    }
}
