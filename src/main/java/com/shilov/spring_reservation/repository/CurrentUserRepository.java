package com.shilov.spring_reservation.repository;

import com.shilov.spring_reservation.common.exceptions.RepositoryException;
import com.shilov.spring_reservation.models.User;

import java.util.Optional;

public interface CurrentUserRepository {

    Optional<User> getCurrentUser() throws RepositoryException;

    void setCurrentUser(User user) throws RepositoryException;
}
