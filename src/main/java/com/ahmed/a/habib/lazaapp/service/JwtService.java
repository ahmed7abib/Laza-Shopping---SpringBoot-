package com.ahmed.a.habib.lazaapp.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {

    String extractUserName(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

    String generateToken(UserDetails userDetails);

    String generateRefreshToken(Map<String, Object> extractClaims, UserDetails userDetails);
}
