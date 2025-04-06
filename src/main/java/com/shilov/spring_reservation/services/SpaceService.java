package com.shilov.spring_reservation.services;

import com.shilov.spring_reservation.common.exceptions.ServiceException;
import com.shilov.spring_reservation.entities.ReservationDateTime;
import com.shilov.spring_reservation.entities.Space;

import java.util.List;

public interface SpaceService {

    Long addNewSpace(Space space) throws ServiceException;

    void deleteSpace(Long id) throws ServiceException;

    void updateSpace(Long id, Space update) throws ServiceException;

    List<Space> getAvailableForReservationSpaces(ReservationDateTime reservationDateTime) throws ServiceException;

    Space getSpaceById(Long id) throws ServiceException;

    boolean checkSpaceReservationAvailability(Long spaceId, ReservationDateTime reservationDateTime) throws ServiceException;
}
