package com.shilov.spring_reservation.services.impl;

import com.shilov.spring_reservation.common.enums.UserRole;
import com.shilov.spring_reservation.common.exceptions.DataNotFoundException;
import com.shilov.spring_reservation.common.exceptions.EntityAlreadyExistsException;
import com.shilov.spring_reservation.entities.User;
import com.shilov.spring_reservation.models.UserModel;
import com.shilov.spring_reservation.models.requests.LoginRequest;
import com.shilov.spring_reservation.models.requests.RegistrationRequest;
import com.shilov.spring_reservation.models.responses.LoginResponse;
import com.shilov.spring_reservation.repository.UserRepository;
import com.shilov.spring_reservation.services.AuthService;
import com.shilov.spring_reservation.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public AuthServiceImpl(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserModel register(RegistrationRequest request, UserRole role) throws EntityAlreadyExistsException {
        if (userRepository.findUserByLogin(request.login()).isPresent()) {
            throw new EntityAlreadyExistsException("User with login " + request.login() + " already exists");
        }
        return userRepository
                .save(new User(request.login(), role, passwordEncoder.encode(request.password())))
                .toUserModel();
    }

    @Override
    public LoginResponse authenticate(LoginRequest request) throws DataNotFoundException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.login(), request.password()));
        User user = userRepository.findUserByLogin(request.login())
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        return new LoginResponse(jwtService.generateAccessToken(user));
    }
}
