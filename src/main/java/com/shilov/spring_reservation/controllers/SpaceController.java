package com.shilov.spring_reservation.controllers;

import com.shilov.spring_reservation.controllers.requests.ReservationDateTimeInput;
import com.shilov.spring_reservation.controllers.requests.UpdateSpaceRequest;
import com.shilov.spring_reservation.controllers.responses.Response;

public interface SpaceController {

    Response createSpace(String spaceType, String hourlyRate);

    Response deleteSpace(String id);

    Response updateSpace(UpdateSpaceRequest request);

    Response getAvailableSpaces(ReservationDateTimeInput request);
}
