package com.shilov.spring_reservation.services;

import com.shilov.spring_reservation.common.exceptions.ServiceException;
import com.shilov.spring_reservation.entities.ReservationDateTime;
import com.shilov.spring_reservation.models.SpaceModel;

import java.util.List;

public interface SpaceService {

    Long addNewSpace(SpaceModel spaceModel) throws ServiceException;

    void deleteSpace(Long id) throws ServiceException;

    void updateSpace(Long id, SpaceModel update) throws ServiceException;

    List<SpaceModel> getAvailableForReservationSpaces(ReservationDateTime reservationDateTime) throws ServiceException;

    SpaceModel getSpaceById(Long id) throws ServiceException;

    boolean checkSpaceReservationAvailability(Long spaceId, ReservationDateTime reservationDateTime) throws ServiceException;
}
