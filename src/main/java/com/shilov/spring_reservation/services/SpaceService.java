package com.shilov.spring_reservation.services;

import com.shilov.spring_reservation.common.exceptions.ServiceException;
import com.shilov.spring_reservation.models.ReservationDateTime;
import com.shilov.spring_reservation.models.Space;

import java.util.List;

public interface SpaceService {

    void addNewSpace(Space space) throws ServiceException;

    void deleteSpace(String id) throws ServiceException;

    void updateSpace(String id, Space update) throws ServiceException;

    List<Space> getAvailableForReservationSpaces(ReservationDateTime reservationDateTime) throws ServiceException;

    Space getSpaceById(String id) throws ServiceException;
}
