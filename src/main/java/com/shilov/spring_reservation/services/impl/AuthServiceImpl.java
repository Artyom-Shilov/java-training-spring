package com.shilov.spring_reservation.services.impl;

import com.shilov.spring_reservation.common.enums.UserRole;
import com.shilov.spring_reservation.common.exceptions.RepositoryException;
import com.shilov.spring_reservation.common.exceptions.ServiceException;
import com.shilov.spring_reservation.models.User;
import com.shilov.spring_reservation.repository.CurrentUserRepository;
import com.shilov.spring_reservation.repository.GlobalUserRepository;
import com.shilov.spring_reservation.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final CurrentUserRepository currentUserRepository;
    private final GlobalUserRepository globalUserRepository;

    @Autowired
    public AuthServiceImpl(CurrentUserRepository currentUserRepository,
                           GlobalUserRepository globalUserRepository) {
        this.currentUserRepository = currentUserRepository;
        this.globalUserRepository = globalUserRepository;
    }

    @Override
    public void signIn(String login, UserRole role) throws ServiceException {
        try {
            User user;
            Optional<User> searchResult = globalUserRepository.findUserByLogin(login);
            if (searchResult.isEmpty()) {
                user = new User(login, role);
                Long id = globalUserRepository.saveUser(user);
                user.setId(id);
            } else {
                user = searchResult.get();
            }
            currentUserRepository.setCurrentUser(user);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void signOut() throws ServiceException {
        try {
            currentUserRepository.setCurrentUser(null);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getCurrentUser() throws ServiceException {
        try {
            return currentUserRepository.getCurrentUser().orElseThrow(() -> new ServiceException("No user is logged in"));
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}
