package com.shilov.spring_reservation.repository.impl;

import com.shilov.spring_reservation.models.User;
import com.shilov.spring_reservation.repository.GlobalUserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class JpaGlobalUserRepository implements GlobalUserRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveUser(User user) {
        em.persist(user);
        return user.getId();
    }

    @Override
    public Optional<User> findUserById(Long id) {
            User user = em.find(User.class, id);
            return user != null ? Optional.of(user) : Optional.empty();
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        try {
            return Optional.of(
                    em.createQuery("SELECT u FROM User u WHERE u.login = :login", User.class)
                            .setParameter("login", login).getSingleResult()
            );
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
