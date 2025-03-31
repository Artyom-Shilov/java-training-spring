package com.shilov.spring_reservation.repository.impl;

import com.shilov.spring_reservation.common.exceptions.RepositoryException;
import com.shilov.spring_reservation.models.Reservation;
import com.shilov.spring_reservation.models.ReservationDateTime;
import com.shilov.spring_reservation.models.User;
import com.shilov.spring_reservation.repository.ReservationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaReservationRepository implements ReservationRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Reservation> getAllReservations() {
            return em.createQuery("SELECT r FROM Reservation r", Reservation.class).getResultList();
    }

    @Override
    public Optional<Reservation> getReservationById(Long id) {
            Reservation reservation = em.find(Reservation.class, id);
            return reservation != null ? Optional.of(reservation) : Optional.empty();
    }

    @Override
    public List<Reservation> getReservationsByCustomer(User customer) {
            return em.createQuery("SELECT r FROM Reservation r WHERE r.customer.id = customer.id",Reservation.class)
                    .getResultList();
    }

    @Override
    public Long addReservation(Reservation reservation) throws RepositoryException {
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(reservation);
            transaction.commit();
            return reservation.getId();
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            throw new RepositoryException(e);
        }
    }

    @Override
    public void updateReservation(Long id, Reservation newData) throws RepositoryException {
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            Reservation reservation = em.find(Reservation.class, id);
            if (reservation != null) {
                newData.setId(id);
                em.merge(newData);
                transaction.commit();
            } else {
                transaction.rollback();
                throw new RepositoryException("Reservation not found");
            }
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Reservation> getReservationsIntersectedWithTimeRange(ReservationDateTime dateTimeForIntersection) {
            String reservationIntersectionQuery = """
                    SELECT r FROM Reservation r WHERE r.reservationDateTime.date = :date
                    AND r.reservationDateTime.startTime <= :startLimit
                    AND r.reservationDateTime.endTime >= :endLimit
                    """;
           return em.createQuery(reservationIntersectionQuery, Reservation.class)
                    .setParameter("date", dateTimeForIntersection.getDate())
                    .setParameter("startLimit", dateTimeForIntersection.getEndTime())
                    .setParameter("endLimit", dateTimeForIntersection.getStartTime())
                    .getResultList();
    }
}
