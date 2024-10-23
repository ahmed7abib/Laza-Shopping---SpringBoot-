package com.ahmed.a.habib.lazaapp.service.impl;

import com.ahmed.a.habib.lazaapp.model.dto.UserDto;
import com.ahmed.a.habib.lazaapp.model.entity.Role;
import com.ahmed.a.habib.lazaapp.model.entity.UserEntity;
import com.ahmed.a.habib.lazaapp.model.mapper.UserMapper;
import com.ahmed.a.habib.lazaapp.model.response.AuthResponse;
import com.ahmed.a.habib.lazaapp.model.response.Status;
import com.ahmed.a.habib.lazaapp.repository.UserRepository;
import com.ahmed.a.habib.lazaapp.service.AuthService;
import com.ahmed.a.habib.lazaapp.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper = UserMapper.INSTANCE;


    public AuthResponse registerUser(UserDto user) {

        Optional<UserEntity> userEntity = userRepository.findByEmail(user.getEmail());

        if (userEntity.isPresent()) {
            return buildLoginResponse(1, "This email already exist!", null, null);
        } else {
            String notHashedPassword = user.getPassword();
            String hashedPassword = passwordEncoder.encode(user.getPassword());

            user.setRole(Role.USER);
            user.setPassword(hashedPassword);

            userRepository.save(userMapper.toEntity(user));

            return loginUser(user.getEmail(), notHashedPassword);
        }
    }

    public AuthResponse loginUser(String email, String password) {

        Optional<UserEntity> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            UserDto userDto = userMapper.toDto(userOptional.get());
            boolean isMatch = passwordEncoder.matches(password, userDto.getPassword());

            if (isMatch) {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
                final String token = jwtService.generateToken(userMapper.toEntity(userDto));

                return buildLoginResponse(
                        0,
                        "Success",
                        token,
                        userDto
                );
            } else {
                return buildLoginResponse(
                        1,
                        "Incorrect Password!",
                        null,
                        null
                );
            }
        } else {
            return buildLoginResponse(1,
                    "Incorrect Email!",
                    null,
                    null
            );
        }
    }

    //    @Override
    //    public AuthResponse refreshToken(String token) {
    //        String userEmail = jwtService.extractUserName(token);
    //        Optional<UserEntity> user = userRepository.findByEmail(userEmail);
    //
    //        if (user.isPresent()) {
    //            UserDto userDto = userMapper.toDto(user.get());
    //
    //            if (jwtService.isTokenValid(token, user.get())) {
    //                String newToken = jwtService.generateToken(user.get());
    //                return buildLoginResponse(0, "Refreshed Token", newToken, userDto);
    //            } else {
    //                return buildLoginResponse(1, "Invalid Token", null, null);
    //            }
    //        } else {
    //            return buildLoginResponse(1, "User not found", null, null);
    //        }
    //    }

    private AuthResponse buildLoginResponse(int statusCode, String message, String token, UserDto userDto) {
        Status status = Status.builder()
                .statusCode(statusCode)
                .statusMessage(message)
                .build();

        return AuthResponse.builder()
                .status(status)
                .token(token)
                .userDto(userDto)
                .build();
    }
}