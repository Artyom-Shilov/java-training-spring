package com.shilov.spring_reservation.services;

import com.shilov.spring_reservation.common.exceptions.ServiceException;
import com.shilov.spring_reservation.models.ReservationModel;

import java.util.List;

public interface ReservationService {

    void cancelReservation(Long reservationId, String userLogin) throws ServiceException;

    Long makeReservation(ReservationModel reservationModel) throws ServiceException;

    List<ReservationModel> getAllReservations() throws ServiceException;

    List<ReservationModel> getUserReservations(Long customerId) throws ServiceException;

    ReservationModel getReservationById(Long reservationId) throws ServiceException;
}
