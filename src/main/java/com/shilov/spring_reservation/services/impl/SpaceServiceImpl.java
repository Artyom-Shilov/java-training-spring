package com.shilov.spring_reservation.services.impl;

import com.shilov.spring_reservation.common.enums.ReservationStatus;
import com.shilov.spring_reservation.common.exceptions.RepositoryException;
import com.shilov.spring_reservation.common.exceptions.ServiceException;
import com.shilov.spring_reservation.models.Reservation;
import com.shilov.spring_reservation.models.ReservationDateTime;
import com.shilov.spring_reservation.models.Space;
import com.shilov.spring_reservation.repository.ReservationRepository;
import com.shilov.spring_reservation.repository.SpaceRepository;
import com.shilov.spring_reservation.services.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpaceServiceImpl implements SpaceService {

    private final SpaceRepository spaceRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public SpaceServiceImpl(SpaceRepository spaceRepository, ReservationRepository reservationRepository) {
        this.spaceRepository = spaceRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void addNewSpace(Space space) throws ServiceException {
        try {
            spaceRepository.addSpace(space);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteSpace(String id) throws ServiceException {
        try {
            spaceRepository.deleteSpace(parseSpaceId(id));
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateSpace(String id, Space space) throws ServiceException {
        try {
            spaceRepository.updateSpace(parseSpaceId(id), space);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Space> getAvailableForReservationSpaces(ReservationDateTime reservationDateTime) throws ServiceException {
        try {
            List<Space> spacesWithDateTimeIntersection = reservationRepository
                    .getReservationsIntersectedWithTimeRange(reservationDateTime)
                    .stream()
                    .filter(r -> r.getStatus() == ReservationStatus.ACTIVE)
                    .map(Reservation::getSpace).toList();
            List<Space> spaces = spaceRepository.getAllSpaces();
            spaces.removeAll(spacesWithDateTimeIntersection);
            return spaces;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Space getSpaceById(String id) throws ServiceException {
        try {
            return spaceRepository.getSpaceById(parseSpaceId(id)).orElseThrow(() -> new ServiceException("Space not found"));
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    private Long parseSpaceId(String id) throws ServiceException {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new ServiceException("Invalid space id format");
        }
    }
}
