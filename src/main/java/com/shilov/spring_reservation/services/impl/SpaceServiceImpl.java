package com.shilov.spring_reservation.services.impl;

import com.shilov.spring_reservation.common.enums.ReservationStatus;
import com.shilov.spring_reservation.common.exceptions.DataNotFoundException;
import com.shilov.spring_reservation.common.exceptions.ReservationConflictException;
import com.shilov.spring_reservation.entities.Reservation;
import com.shilov.spring_reservation.entities.Space;
import com.shilov.spring_reservation.models.ReservationDateTimeModel;
import com.shilov.spring_reservation.models.SpaceModel;
import com.shilov.spring_reservation.repository.ReservationRepository;
import com.shilov.spring_reservation.repository.SpaceRepository;
import com.shilov.spring_reservation.services.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    @Transactional
    public Long addNewSpace(SpaceModel spaceModel) {
        return spaceRepository.save(new Space(spaceModel)).getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "spaces", key = "#p0")
    public void deleteSpace(Long id) throws DataNotFoundException {
        if (spaceRepository.findSpaceById(id).isEmpty()) {
            throw new DataNotFoundException(Space.class, id);
        }
        spaceRepository.deleteSpaceById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CachePut(value = "spaces", key = "#p0")
    public SpaceModel updateSpace(Long id, SpaceModel spaceModel) throws DataNotFoundException {
        if (spaceRepository.findSpaceById(id).isEmpty()) {
            throw new DataNotFoundException(Space.class, id);
        }
        Space space = new Space(spaceModel);
        space.setId(id);
        return spaceRepository.save(space).toSpaceModel();
    }

    @Override
    public List<SpaceModel> getAvailableForReservationSpaces(ReservationDateTimeModel reservationDateTime) {
        List<Space> spacesWithDateTimeIntersection = reservationRepository
                .findReservationsIntersectedWithTimeRange(
                        reservationDateTime.date(),
                        reservationDateTime.startTime(),
                        reservationDateTime.endTime())
                .stream()
                .filter(r -> r.getStatus() == ReservationStatus.ACTIVE)
                .map(Reservation::getSpace).toList();
        List<Space> spaces = spaceRepository.findAll();
        spaces.removeAll(spacesWithDateTimeIntersection);
        return spaces.stream().map((Space::toSpaceModel)).toList();
    }

    @Override
    @Cacheable(value = "spaces", key = "#p0")
    public SpaceModel getSpaceById(Long id) throws DataNotFoundException {
        return spaceRepository.findSpaceById(id)
                .orElseThrow(() -> new DataNotFoundException(Space.class, id))
                .toSpaceModel();
    }

    @Override
    public void checkSpaceReservationAvailability(Long spaceId, ReservationDateTimeModel reservationDateTime) throws ReservationConflictException {
        var optional = getAvailableForReservationSpaces(reservationDateTime)
                .stream().filter(s -> s.id().equals(spaceId))
                .findAny();
        if (optional.isEmpty()) {
            throw new ReservationConflictException("Space with id " + spaceId + " is not available for reservation");
        }
    }
}
