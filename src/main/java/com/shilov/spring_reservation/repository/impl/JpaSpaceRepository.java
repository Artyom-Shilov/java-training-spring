package com.shilov.spring_reservation.repository.impl;

import com.shilov.spring_reservation.common.exceptions.RepositoryException;
import com.shilov.spring_reservation.models.Space;
import com.shilov.spring_reservation.repository.SpaceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaSpaceRepository implements SpaceRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Space> getAllSpaces() {
           return em.createQuery("SELECT s FROM Space s", Space.class).getResultList();
    }

    @Override
    public Optional<Space> getSpaceById(Long id) {
            Space space = em.find(Space.class, id);
            return space != null ? Optional.of(space) : Optional.empty();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addSpace(Space space) {
        em.persist(space);
        return space.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSpace(Long id) throws RepositoryException {
        Space space = em.find(Space.class, id);
        if (space != null) {
            em.remove(space);
        } else {
            throw new RepositoryException("Space not found");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSpace(Long id, Space newData) throws RepositoryException {
        Space space = em.find(Space.class, id);
        if (space != null) {
            newData.setId(id);
            em.merge(newData);
        } else {
            throw new RepositoryException("Space not found");
        }
}

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAllSpaces() {
        em.createQuery("DELETE FROM Space").executeUpdate();
    }
}
