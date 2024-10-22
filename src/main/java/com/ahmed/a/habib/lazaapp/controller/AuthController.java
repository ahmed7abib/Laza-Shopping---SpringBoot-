package com.ahmed.a.habib.lazaapp.controller;

import com.ahmed.a.habib.lazaapp.model.dto.UserDto;
import com.ahmed.a.habib.lazaapp.model.response.LoginResponse;
import com.ahmed.a.habib.lazaapp.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("login")
    public LoginResponse login(@RequestParam("email") String email, @RequestParam("password") String password) {
        return authService.loginUser(email, password);
    }

    @PostMapping("register")
    public Optional<UserDto> register(@RequestBody @Valid UserDto userDto) {
        return authService.registerUser(userDto);
    }
}
