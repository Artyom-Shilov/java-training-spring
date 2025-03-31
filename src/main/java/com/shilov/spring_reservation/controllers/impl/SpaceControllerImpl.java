package com.shilov.spring_reservation.controllers.impl;

import com.shilov.spring_reservation.common.enums.ResponseStatus;
import com.shilov.spring_reservation.common.enums.SpaceType;
import com.shilov.spring_reservation.common.exceptions.ReservationDateTimeFormatException;
import com.shilov.spring_reservation.common.exceptions.ServiceException;
import com.shilov.spring_reservation.controllers.SpaceController;
import com.shilov.spring_reservation.controllers.requests.ReservationDateTimeInput;
import com.shilov.spring_reservation.controllers.requests.UpdateSpaceRequest;
import com.shilov.spring_reservation.controllers.responses.Response;
import com.shilov.spring_reservation.models.ReservationDateTime;
import com.shilov.spring_reservation.models.Space;
import com.shilov.spring_reservation.services.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class SpaceControllerImpl implements SpaceController {

    private final SpaceService spaceService;

    @Autowired
    public SpaceControllerImpl(SpaceService spaceService) {
        this.spaceService = spaceService;
    }

    public Response createSpace(String spaceType, String hourlyRate) {
        Response response;
        String successMessage = "Space was successfully created";
        try {
            spaceService.addNewSpace(new Space(
                    SpaceType.valueOf(spaceType),
                    Integer.parseInt(hourlyRate)
            ));
            response = new Response(ResponseStatus.SUCCESS, successMessage);
        } catch (ServiceException | IllegalArgumentException e) {
            response = new Response(ResponseStatus.FAILURE, e.getMessage());
        }
        return response;
    }

    public Response deleteSpace(String id) {
        Response response;
        String successMessage = "Space was successfully deleted";
        try {
            spaceService.deleteSpace(id);
            response = new Response(ResponseStatus.SUCCESS, successMessage);
        } catch (ServiceException e) {
            response = new Response(ResponseStatus.FAILURE, e.getMessage());
        }
        return response;
    }

    public Response updateSpace (UpdateSpaceRequest request) {
        Response response;
        String successMessage = "Space was successfully updated";
        try {
            Space updatedData = new Space(
                    SpaceType.valueOf(request.getUpdatedSpaceType()),
                    Integer.parseInt(request.getUpdatedHourlyRate())
            );
            spaceService.updateSpace(request.getSpaceId(),updatedData);
            response = new Response(ResponseStatus.SUCCESS, successMessage);
        } catch (ServiceException | IllegalArgumentException e) {
            response = new Response(ResponseStatus.FAILURE, e.getMessage());
        }
        return response;
    }

    public Response getAvailableSpaces(ReservationDateTimeInput request) {
        List<Space> availableSpaces;
        StringBuilder output = new StringBuilder();
        Response response;
        try {
            availableSpaces = spaceService.getAvailableForReservationSpaces(
                    new ReservationDateTime(request.getDate(), request.getStartTime(), request.getEndTime()));
            for (int i = 0; i < availableSpaces.size(); i++) {
                output.append(i + 1).append(": ").append(availableSpaces.get(i)).append("\n");
            }
            if (output.isEmpty()) {
                String noSpacesMessage = "No available spaces found";
                output.append(noSpacesMessage);
            }
            response = new Response(ResponseStatus.SUCCESS, output.toString());
        } catch (ServiceException | ReservationDateTimeFormatException e) {
            response = new Response(ResponseStatus.FAILURE, e.getMessage());
        }
        return response;
    }
}
