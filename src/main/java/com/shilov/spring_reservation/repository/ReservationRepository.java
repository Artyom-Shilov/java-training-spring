package com.shilov.spring_reservation.repository;

import com.shilov.spring_reservation.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findReservationById(Long id);

    List<Reservation> findReservationsByCustomerId(Long customerId);

    @Query("""
            SELECT r FROM Reservation r WHERE r.reservationDateTime.date = ?1
                                AND r.reservationDateTime.endTime >= ?2
                                AND r.reservationDateTime.startTime <= ?3
            """)
    List<Reservation> findReservationsIntersectedWithTimeRange(LocalDate date, LocalTime startTime, LocalTime endTime);
}
