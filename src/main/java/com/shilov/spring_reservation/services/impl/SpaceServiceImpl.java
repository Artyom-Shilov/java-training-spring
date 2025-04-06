package com.shilov.spring_reservation.services.impl;

import com.shilov.spring_reservation.common.enums.ReservationStatus;
import com.shilov.spring_reservation.common.exceptions.RepositoryException;
import com.shilov.spring_reservation.common.exceptions.ServiceException;
import com.shilov.spring_reservation.entities.Reservation;
import com.shilov.spring_reservation.entities.ReservationDateTime;
import com.shilov.spring_reservation.entities.Space;
import com.shilov.spring_reservation.repository.ReservationRepository;
import com.shilov.spring_reservation.repository.SpaceRepository;
import com.shilov.spring_reservation.services.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(rollbackFor = Exception.class)
    public Long addNewSpace(Space space) throws ServiceException {
        try {
            return spaceRepository.addSpace(space);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSpace(Long id) throws ServiceException {
        try {
            spaceRepository.deleteSpace(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSpace(Long id, Space space) throws ServiceException {
        try {
            spaceRepository.updateSpace(id, space);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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
    public Space getSpaceById(Long id) throws ServiceException {
        try {
            return spaceRepository.getSpaceById(id).orElseThrow(() -> new ServiceException("Space not found"));
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean checkSpaceReservationAvailability(Long spaceId, ReservationDateTime reservationDateTime) throws ServiceException {
        var optional = getAvailableForReservationSpaces(reservationDateTime)
                .stream().filter(s -> s.getId().equals(spaceId))
                .findAny();
        return optional.isPresent();
    }
}
