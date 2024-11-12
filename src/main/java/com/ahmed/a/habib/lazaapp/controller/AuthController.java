package com.ahmed.a.habib.lazaapp.controller;

import com.ahmed.a.habib.lazaapp.model.dto.UserDto;
import com.ahmed.a.habib.lazaapp.model.response.AuthResponse;
import com.ahmed.a.habib.lazaapp.model.response.Status;
import com.ahmed.a.habib.lazaapp.service.auth.AuthService;
import com.ahmed.a.habib.lazaapp.service.auth.OTPService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final OTPService otpService;
    private final AuthService authService;

    @GetMapping("login")
    public ResponseEntity<AuthResponse> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        return ResponseEntity.ok(authService.loginUser(username, password));
    }

    @PostMapping("register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid UserDto userDto) {
        return ResponseEntity.ok(authService.addUser(userDto));
    }

    @GetMapping("sendOtp")
    public ResponseEntity<Status> forgetPass(@RequestParam(value = "email") String email) {
        return ResponseEntity.ok(otpService.sendOtp(email));
    }

    @GetMapping("confirmOtp")
    public ResponseEntity<Status> confirmOtp(@RequestParam(value = "email") String email, @RequestParam(value = "otp") String otp) {
        return ResponseEntity.ok(otpService.confirmOtp(email, otp));
    }

    @GetMapping("updatePass")
    public ResponseEntity<Status> updatePass(@RequestParam(value = "email") String email, @RequestParam(value = "newPass") String newPass) {
        return ResponseEntity.ok(authService.updatePass(email, newPass));
    }
}