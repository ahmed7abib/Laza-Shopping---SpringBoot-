package com.ahmed.a.habib.lazaapp.service;

import com.ahmed.a.habib.lazaapp.model.dto.UserDto;
import com.ahmed.a.habib.lazaapp.model.response.AuthResponse;

public interface AuthService {
    AuthResponse registerUser(UserDto user);

    AuthResponse loginUser(String email, String password);

    AuthResponse refreshToken(String token);
}
