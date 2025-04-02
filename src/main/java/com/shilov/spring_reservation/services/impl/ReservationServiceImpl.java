package com.shilov.spring_reservation.services.impl;

import com.shilov.spring_reservation.common.enums.ReservationStatus;
import com.shilov.spring_reservation.common.enums.UserRole;
import com.shilov.spring_reservation.common.exceptions.RepositoryException;
import com.shilov.spring_reservation.common.exceptions.ServiceException;
import com.shilov.spring_reservation.models.Reservation;
import com.shilov.spring_reservation.models.ReservationDateTime;
import com.shilov.spring_reservation.models.Space;
import com.shilov.spring_reservation.models.User;
import com.shilov.spring_reservation.models.builders.ReservationBuilder;
import com.shilov.spring_reservation.repository.ReservationRepository;
import com.shilov.spring_reservation.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationBuilder reservationBuilder;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
        reservationBuilder = new ReservationBuilder();
    }

    @Override
    public void cancelReservation(Reservation reservation, User user) throws ServiceException {
        validateUserForReservationCancel(user, reservation);
        reservation.setStatus(ReservationStatus.CANCELLED);
        try {
            reservationRepository.updateReservation(reservation.getId(), reservation);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public void validateUserForReservationCancel(User user, Reservation reservation) throws ServiceException {
        if (user.getRole() != UserRole.ADMIN || user.equals(reservation.getCustomer())) {
            throw new ServiceException("User does have rights to cancel the reservation");
        }
    }

    @Override
    public void makeReservation(Space space, User customer, ReservationDateTime reservationDateTime) throws ServiceException {
        Reservation newReservation = reservationBuilder.setSpace(space).setCustomer(customer)
                .setReservationDateTime(reservationDateTime).setStatus(ReservationStatus.ACTIVE).createReservation();
        validateNewReservationTime(newReservation);
        try {
            reservationRepository.addReservation(newReservation);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    private void validateNewReservationTime(Reservation newReservation) throws ServiceException {
        List<Space> spacesWithDateTimeIntersection;
        try {
            spacesWithDateTimeIntersection = reservationRepository
                    .getReservationsIntersectedWithTimeRange(newReservation.getReservationDateTime())
                    .stream().map(Reservation::getSpace).toList();
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        if (spacesWithDateTimeIntersection.contains(newReservation.getSpace())) {
            throw new ServiceException("Failed to make a reservation due to time conflict");
        }
    }

    @Override
    public List<Reservation> getAllReservations() throws ServiceException {
        try {
            return reservationRepository.getAllReservations();
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Reservation> getUserReservations(User customer) throws ServiceException {
        try {
            return reservationRepository.getReservationsByCustomer(customer);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Reservation getReservationById(String reservationId) throws ServiceException {
        try {
            return reservationRepository.getReservationById(parseReservationId(reservationId))
                    .orElseThrow(() -> new ServiceException("Reservation not found"));
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    private Long parseReservationId(String id) throws ServiceException {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new ServiceException("Invalid reservation id format");
        }
    }
}
