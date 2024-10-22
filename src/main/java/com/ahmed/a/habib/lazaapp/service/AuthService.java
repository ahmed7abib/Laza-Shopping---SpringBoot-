package com.ahmed.a.habib.lazaapp.service;

import com.ahmed.a.habib.lazaapp.model.dto.UserDto;
import com.ahmed.a.habib.lazaapp.model.response.LoginResponse;

import java.util.Optional;

public interface AuthService {

    Optional<UserDto> registerUser(UserDto user);

    LoginResponse loginUser(String email, String password);
}
