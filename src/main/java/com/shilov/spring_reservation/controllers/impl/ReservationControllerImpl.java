package com.shilov.spring_reservation.controllers.impl;

import com.shilov.spring_reservation.common.enums.ResponseStatus;
import com.shilov.spring_reservation.common.exceptions.ReservationDateTimeFormatException;
import com.shilov.spring_reservation.common.exceptions.ServiceException;
import com.shilov.spring_reservation.controllers.ReservationController;
import com.shilov.spring_reservation.controllers.requests.MakeCurrentUserReservationRequest;
import com.shilov.spring_reservation.controllers.requests.ReservationDateTimeInput;
import com.shilov.spring_reservation.controllers.responses.Response;
import com.shilov.spring_reservation.models.Reservation;
import com.shilov.spring_reservation.models.ReservationDateTime;
import com.shilov.spring_reservation.services.ReservationService;
import com.shilov.spring_reservation.services.AuthService;
import com.shilov.spring_reservation.services.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ReservationControllerImpl implements ReservationController {

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

    public Response getAllReservations() {
        StringBuilder output = new StringBuilder();
        List<Reservation> allReservations;
        Response response;
        try {
            allReservations = reservationService.getAllReservations();
            for (int i = 0; i < allReservations.size(); i++) {
                output.append(i + 1).append(": ").append(allReservations.get(i)).append("\n");
            }
            if (output.isEmpty()) {
                String noReservationsMessage = "System does not have any reservations";
                output.append(noReservationsMessage);
            }
            response = new Response(ResponseStatus.SUCCESS, output.toString());
        } catch (ServiceException e) {
            response = new Response(ResponseStatus.FAILURE, e.getMessage());
        }
        return response;
    }

    public Response makeCurrentUserReservation(MakeCurrentUserReservationRequest request) {
        Response response;
        String successMessage = "Reservation was successfully made";
        ReservationDateTimeInput dateTimeInput = request.getReservationDateTimeInput();
        try {
            reservationService.makeReservation(
                    spaceService.getSpaceById(request.getSpaceId()),
                    authService.getCurrentUser(),
                    new ReservationDateTime(dateTimeInput.getDate(), dateTimeInput.getStartTime(),
                            dateTimeInput.getEndTime())
            );
            response = new Response(ResponseStatus.SUCCESS, successMessage);
        } catch (ServiceException | ReservationDateTimeFormatException e) {
            response = new Response(ResponseStatus.FAILURE, e.getMessage());
        }
        return response;
    }

    public Response getCurrentUserReservations() {
        Response response;
        StringBuilder output = new StringBuilder();
        List<Reservation> currentUserReservations;
        try {
            currentUserReservations = reservationService.getUserReservations(authService.getCurrentUser());
            for (int i = 0; i < currentUserReservations.size(); i++) {
                output.append(i + 1).append(": ").append(currentUserReservations.get(i)).append("\n");
            }
            if (output.isEmpty()) {
                String noReservationMessage = "User does not have any reservations";
                output.append(noReservationMessage);
            }
            response = new Response(ResponseStatus.SUCCESS, output.toString());
        } catch (ServiceException e) {
            response = new Response(ResponseStatus.FAILURE, e.getMessage());
        }
        return response;
    }

    public Response cancelReservation(String reservationId) {
        String successMessage = "Reservation was successfully cancelled";
        Response response;
        try {
            reservationService.cancelReservation(
                    reservationService.getReservationById(reservationId),
                    authService.getCurrentUser());
            response = new Response(ResponseStatus.SUCCESS, successMessage);
        } catch (ServiceException e) {
            response = new Response(ResponseStatus.FAILURE, e.getMessage());
        }
        return response;
    }
}
