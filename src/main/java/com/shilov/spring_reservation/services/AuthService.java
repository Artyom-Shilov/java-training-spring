package com.shilov.spring_reservation.services;

import com.shilov.spring_reservation.common.enums.UserRole;
import com.shilov.spring_reservation.common.exceptions.ServiceException;
import com.shilov.spring_reservation.models.User;

public interface AuthService {

    void signIn(String login, UserRole role) throws ServiceException;

    void signOut() throws ServiceException;

    User getCurrentUser() throws ServiceException;
}
