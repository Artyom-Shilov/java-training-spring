package com.shilov.spring_reservation.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String extractUsername(String token);

    String generateAccessToken(UserDetails userDetails);

    Long getExpirationTime();

    boolean isTokenValid(String token, UserDetails userDetails);
}
