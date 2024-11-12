package com.ahmed.a.habib.lazaapp.service.auth;

import com.ahmed.a.habib.lazaapp.model.dto.UserDto;
import com.ahmed.a.habib.lazaapp.model.response.AuthResponse;
import com.ahmed.a.habib.lazaapp.model.response.Status;

public interface AuthService {

    AuthResponse addUser(UserDto user);

    AuthResponse loginUser(String username, String password);

    Status updatePass(String email, String newPass);
}
