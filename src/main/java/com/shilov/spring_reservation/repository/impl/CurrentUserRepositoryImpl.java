package com.shilov.spring_reservation.repository.impl;

import com.shilov.spring_reservation.entities.User;
import com.shilov.spring_reservation.repository.CurrentUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CurrentUserRepositoryImpl implements CurrentUserRepository {

    private User currentUser;

    @Override
    public Optional<User> getCurrentUser() {
        return Optional.ofNullable(currentUser);
    }

    @Override
    public void setCurrentUser(User user) {
        currentUser = user;
    }
}
