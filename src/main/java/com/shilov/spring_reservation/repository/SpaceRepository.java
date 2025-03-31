package com.shilov.spring_reservation.repository;

import com.shilov.spring_reservation.common.exceptions.RepositoryException;
import com.shilov.spring_reservation.models.Space;

import java.util.List;
import java.util.Optional;

public interface SpaceRepository {

    List<Space> getAllSpaces() throws RepositoryException;

    Optional<Space> getSpaceById(Long id) throws RepositoryException;

    Long addSpace(Space space) throws RepositoryException;

    void deleteSpace(Long id) throws RepositoryException;

    void updateSpace(Long id, Space newData) throws RepositoryException;

    void deleteAllSpaces() throws RepositoryException;
}
