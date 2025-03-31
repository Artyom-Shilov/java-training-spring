package com.shilov.spring_reservation.repository;

import com.shilov.spring_reservation.common.exceptions.RepositoryException;
import com.shilov.spring_reservation.models.User;

import java.util.Optional;

public interface GlobalUserRepository {

    Long saveUser(User user) throws RepositoryException;

    Optional<User> findUserById(Long id) throws RepositoryException;

    Optional<User> findUserByLogin(String login) throws RepositoryException;
}
