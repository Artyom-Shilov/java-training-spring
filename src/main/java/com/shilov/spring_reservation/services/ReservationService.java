package com.shilov.spring_reservation.services;

import com.shilov.spring_reservation.common.exceptions.ServiceException;
import com.shilov.spring_reservation.entities.Reservation;
import com.shilov.spring_reservation.entities.ReservationDateTime;
import com.shilov.spring_reservation.entities.Space;
import com.shilov.spring_reservation.entities.User;

import java.util.List;

public interface ReservationService {

    void cancelReservation(Long reservationId, User user) throws ServiceException;

    Long makeReservation(Space space, User customer, ReservationDateTime reservationDateTime) throws ServiceException;

    List<Reservation> getAllReservations() throws ServiceException;

    List<Reservation> getUserReservations(User customer) throws ServiceException;

    Reservation getReservationById(Long reservationId) throws ServiceException;
}
