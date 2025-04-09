package com.shilov.spring_reservation.services.impl;

import com.shilov.spring_reservation.common.enums.ReservationStatus;
import com.shilov.spring_reservation.common.enums.UserRole;
import com.shilov.spring_reservation.common.exceptions.AuthorizationException;
import com.shilov.spring_reservation.common.exceptions.DataNotFoundException;
import com.shilov.spring_reservation.common.exceptions.ServiceException;
import com.shilov.spring_reservation.entities.Reservation;
import com.shilov.spring_reservation.entities.ReservationDateTime;
import com.shilov.spring_reservation.entities.Space;
import com.shilov.spring_reservation.entities.User;
import com.shilov.spring_reservation.entities.builders.ReservationBuilder;
import com.shilov.spring_reservation.models.ReservationModel;
import com.shilov.spring_reservation.repository.UserRepository;
import com.shilov.spring_reservation.repository.ReservationRepository;
import com.shilov.spring_reservation.repository.SpaceRepository;
import com.shilov.spring_reservation.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final SpaceRepository spaceRepository;
    private final ReservationBuilder reservationBuilder;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  UserRepository userRepository,
                                  SpaceRepository spaceRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.spaceRepository = spaceRepository;
        reservationBuilder = new ReservationBuilder();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CachePut(value = "reservations", key = "#reservationId")
    public void cancelReservation(Long reservationId, Long userId) throws ServiceException {
        Reservation reservation = reservationRepository
                .findReservationById(reservationId)
                .orElseThrow(() -> new DataNotFoundException(Reservation.class, reservationId));
        User user = userRepository
                .findUserById(userId)
                .orElseThrow(() -> new DataNotFoundException(User.class, userId));
        validateUserForReservationCancel(user, reservation);
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
    }

    public void validateUserForReservationCancel(User user, Reservation reservation) throws ServiceException {
        if (user.getRole() == UserRole.ADMIN || user.equals(reservation.getCustomer())) {
            return;
        }
        throw new AuthorizationException("User does have rights to cancel the reservation");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long makeReservation(ReservationModel reservationModel) throws ServiceException {
        User user = userRepository
                .findUserById(reservationModel.customerId())
                .orElseThrow(() -> new DataNotFoundException(User.class, reservationModel.customerId()));
        Space space = spaceRepository
                .findSpaceById(reservationModel.spaceId())
                .orElseThrow(() -> new DataNotFoundException(Space.class, reservationModel.spaceId()));
        Reservation newReservation = reservationBuilder
                .setSpace(space)
                .setCustomer(user)
                .setReservationDateTime(new ReservationDateTime(
                        reservationModel.date(),
                        reservationModel.startTime(),
                        reservationModel.endTime()))
                .setStatus(ReservationStatus.ACTIVE).createReservation();
        validateNewReservationTime(newReservation);
       return reservationRepository.save(newReservation).getId();
    }

    private void validateNewReservationTime(Reservation newReservation) throws ServiceException {
        List<Space> spacesWithDateTimeIntersection = reservationRepository
                .findReservationsIntersectedWithTimeRange(
                        newReservation.getReservationDateTime().getDate(),
                        newReservation.getReservationDateTime().getStartTime(),
                        newReservation.getReservationDateTime().getEndTime())
                .stream().map(Reservation::getSpace).toList();
        if (spacesWithDateTimeIntersection.contains(newReservation.getSpace())) {
            throw new ServiceException("Failed to make a reservation due to time conflict");
        }
    }

    @Override
    public List<ReservationModel> getAllReservations() {
       return reservationRepository.findAll().stream().map(Reservation::toReservationModel).toList();
    }

    @Override
    public List<ReservationModel> getUserReservations(Long customerId) {
        return reservationRepository.findReservationsByCustomerId(customerId).stream()
                .map(Reservation::toReservationModel).toList();
    }

    @Override
    @Cacheable(value = "spaces", key = "#reservationId")
    public ReservationModel getReservationById(Long reservationId) throws DataNotFoundException {
        return reservationRepository.findReservationById(reservationId)
                .orElseThrow(() -> new DataNotFoundException(Reservation.class, reservationId))
                .toReservationModel();
    }
}
