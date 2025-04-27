package com.shilov.spring_reservation.repository;

import com.shilov.spring_reservation.entities.Space;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpaceRepository extends JpaRepository<Space, Long> {

    Optional<Space> findSpaceById(Long id);

    void deleteSpaceById(Long id);
}
