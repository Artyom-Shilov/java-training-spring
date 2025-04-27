package com.shilov.spring_reservation.repository;

import com.shilov.spring_reservation.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserById(Long id);

    Optional<User> findUserByLogin(String login);
}
