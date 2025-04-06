package com.shilov.spring_reservation.repository;

import com.shilov.spring_reservation.common.exceptions.RepositoryException;
import com.shilov.spring_reservation.entities.Reservation;
import com.shilov.spring_reservation.entities.ReservationDateTime;
import com.shilov.spring_reservation.entities.User;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    List<Reservation> getAllReservations() throws RepositoryException;

    Optional<Reservation> getReservationById(Long id) throws RepositoryException;

    List<Reservation> getReservationsByCustomer(User customer) throws RepositoryException;

    Long addReservation(Reservation reservation) throws RepositoryException;

    void updateReservation(Long id, Reservation newData) throws RepositoryException;

    List<Reservation> getReservationsIntersectedWithTimeRange(ReservationDateTime dateTimeForIntersection) throws RepositoryException;
}
