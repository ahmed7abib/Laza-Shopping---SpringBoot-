package com.ahmed.a.habib.lazaapp.service.serviceImpl;

import com.ahmed.a.habib.lazaapp.model.dto.UserDto;
import com.ahmed.a.habib.lazaapp.model.entity.UserEntity;
import com.ahmed.a.habib.lazaapp.model.mapper.UserMapper;
import com.ahmed.a.habib.lazaapp.model.response.LoginResponse;
import com.ahmed.a.habib.lazaapp.model.response.Status;
import com.ahmed.a.habib.lazaapp.repository.AuthRepo;
import com.ahmed.a.habib.lazaapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepo authRepo;
    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final BCryptPasswordEncoder passwordEncoder;

    public Optional<UserDto> registerUser(UserDto user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        UserEntity userEntity = authRepo.save(userMapper.toEntity(user));
        return Optional.of(userMapper.toDto(userEntity));
    }

    public LoginResponse loginUser(String email, String password) {
        Optional<UserEntity> userOptional = authRepo.findByEmail(email);

        if (userOptional.isPresent()) {
            UserDto userDto = userMapper.toDto(userOptional.get());
            boolean isMatch = passwordEncoder.matches(password, userDto.getPassword());

            if (isMatch) {
                return buildLoginResponse(0, "Logged In Successfully!", userDto);
            } else {
                return buildLoginResponse(1, "Invalid Password!", null);
            }
        } else {
            return buildLoginResponse(1, "Invalid Email!", null);
        }
    }

    private LoginResponse buildLoginResponse(int statusCode, String message, UserDto userDto) {
        Status status = Status.builder()
                .statusCode(statusCode)
                .statusMessage(message)
                .build();

        return LoginResponse.builder()
                .status(status)
                .userDto(userDto)
                .build();
    }
}
