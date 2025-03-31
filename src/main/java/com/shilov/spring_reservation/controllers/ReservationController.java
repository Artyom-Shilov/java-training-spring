package com.shilov.spring_reservation.controllers;

import com.shilov.spring_reservation.controllers.requests.MakeCurrentUserReservationRequest;
import com.shilov.spring_reservation.controllers.responses.Response;

public interface ReservationController {

    Response getAllReservations();

    Response makeCurrentUserReservation(MakeCurrentUserReservationRequest request);

    Response getCurrentUserReservations();

    Response cancelReservation(String reservationId);
}
