package com.shilov.spring_reservation.services;

import com.shilov.spring_reservation.common.exceptions.ServiceException;
import com.shilov.spring_reservation.models.ReservationDateTimeModel;
import com.shilov.spring_reservation.models.SpaceModel;

import java.util.List;

public interface SpaceService {

    Long addNewSpace(SpaceModel spaceModel) throws ServiceException;

    void deleteSpace(Long id) throws ServiceException;

    SpaceModel updateSpace(Long id, SpaceModel update) throws ServiceException;

    List<SpaceModel> getAvailableForReservationSpaces(ReservationDateTimeModel reservationDateTime) throws ServiceException;

    SpaceModel getSpaceById(Long id) throws ServiceException;

    void checkSpaceReservationAvailability(Long spaceId, ReservationDateTimeModel reservationDateTime) throws ServiceException;
}
