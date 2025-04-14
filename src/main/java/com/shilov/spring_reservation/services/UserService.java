package com.shilov.spring_reservation.services;

import com.shilov.spring_reservation.common.enums.UserRole;
import com.shilov.spring_reservation.common.exceptions.ServiceException;
import com.shilov.spring_reservation.models.requests.RegistrationRequest;
import com.shilov.spring_reservation.models.UserModel;

public interface UserService {

    UserModel getUser(Long id) throws ServiceException;
}
