package com.ahmed.a.habib.lazaapp.service.auth.impl;

import com.ahmed.a.habib.lazaapp.model.dto.UserDto;
import com.ahmed.a.habib.lazaapp.model.entity.UserEntity;
import com.ahmed.a.habib.lazaapp.model.mapper.UserMapper;
import com.ahmed.a.habib.lazaapp.model.response.AuthResponse;
import com.ahmed.a.habib.lazaapp.model.response.Status;
import com.ahmed.a.habib.lazaapp.repository.UserRepository;
import com.ahmed.a.habib.lazaapp.service.auth.AuthService;
import com.ahmed.a.habib.lazaapp.service.auth.JwtService;
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
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    public AuthResponse addUser(UserDto user) {

        Optional<UserEntity> userEntity = userRepository.findByUsername(user.getUsername());

        if (userEntity.isPresent()) {
            return AuthResponse.buildAuthResponse(1, "This user already exist!", null, null);
        } else {
            String notHashedPassword = user.getPassword();
            String hashedPassword = passwordEncoder.encode(user.getPassword());

            user.setPassword(hashedPassword);

            userRepository.save(userMapper.toEntity(user));

            return loginUser(user.getUsername(), notHashedPassword);
        }
    }

    public AuthResponse loginUser(String username, String password) {

        Optional<UserEntity> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            UserDto userDto = userMapper.toDto(userOptional.get());
            boolean isMatch = passwordEncoder.matches(password, userDto.getPassword());

            if (isMatch) {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
                final String token = jwtService.generateToken(userMapper.toEntity(userDto));

                return AuthResponse.buildAuthResponse(
                        0,
                        "Success",
                        token,
                        userDto
                );
            } else {
                return AuthResponse.buildAuthResponse(
                        1,
                        "Incorrect Password!",
                        null,
                        null
                );
            }
        } else {
            return AuthResponse.buildAuthResponse(1,
                    "User is not found!",
                    null,
                    null
            );
        }
    }

    @Override
    public Status updatePass(String email, String newPass) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);

        if (userEntity.isPresent()) {
            userEntity.get().setPassword(passwordEncoder.encode(newPass));
            userRepository.save(userEntity.get());
            return Status.buildStatusResponse(0, "Password updated!");
        } else {
            return Status.buildStatusResponse(1, "This user is not found!");
        }
    }
}