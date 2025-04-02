package com.shilov.spring_reservation.services;

import com.shilov.spring_reservation.common.exceptions.ServiceException;
import com.shilov.spring_reservation.models.Reservation;
import com.shilov.spring_reservation.models.ReservationDateTime;
import com.shilov.spring_reservation.models.Space;
import com.shilov.spring_reservation.models.User;

import java.util.List;

public interface ReservationService {

    void cancelReservation(Reservation reservation, User user) throws ServiceException;

    void makeReservation(Space space, User customer, ReservationDateTime reservationDateTime) throws ServiceException;

    List<Reservation> getAllReservations() throws ServiceException;

    List<Reservation> getUserReservations(User customer) throws ServiceException;

    Reservation getReservationById(String reservationId) throws ServiceException;
}
