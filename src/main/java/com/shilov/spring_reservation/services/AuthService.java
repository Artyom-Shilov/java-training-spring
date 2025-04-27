package com.shilov.spring_reservation.services;

import com.shilov.spring_reservation.common.enums.UserRole;
import com.shilov.spring_reservation.common.exceptions.ServiceException;
import com.shilov.spring_reservation.models.UserModel;
import com.shilov.spring_reservation.models.requests.LoginRequest;
import com.shilov.spring_reservation.models.requests.RegistrationRequest;
import com.shilov.spring_reservation.models.responses.LoginResponse;

public interface AuthService {

    UserModel register(RegistrationRequest registrationRequest, UserRole role) throws ServiceException;

    LoginResponse authenticate(LoginRequest request) throws ServiceException;
}
