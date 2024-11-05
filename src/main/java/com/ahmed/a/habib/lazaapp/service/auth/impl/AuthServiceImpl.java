package com.ahmed.a.habib.lazaapp.service.auth.impl;

import com.ahmed.a.habib.lazaapp.model.dto.UserDto;
import com.ahmed.a.habib.lazaapp.model.entity.UserEntity;
import com.ahmed.a.habib.lazaapp.model.mapper.UserMapper;
import com.ahmed.a.habib.lazaapp.model.response.AuthResponse;
import com.ahmed.a.habib.lazaapp.model.response.Status;
import com.ahmed.a.habib.lazaapp.repository.UserRepository;
import com.ahmed.a.habib.lazaapp.service.auth.AuthService;
import com.ahmed.a.habib.lazaapp.service.auth.JwtService;
import com.ahmed.a.habib.lazaapp.service.auth.OTPService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.ahmed.a.habib.lazaapp.utils.Utils.generateVerificationCode;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JavaMailSender mailSender;

    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final OTPService otpService;
    private final UserRepository userRepository;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    public AuthResponse registerUser(UserDto user) {

        Optional<UserEntity> userEntity = userRepository.findByUsername(user.getUsername());

        if (userEntity.isPresent()) {
            return buildLoginResponse(1, "This user already exist!", null, null);
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
                    "User is not found!",
                    null,
                    null
            );
        }
    }

    public Status forgetPass(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            boolean isOtpSent = sendVerificationCode(user.get().getUserId(), email);

            if (isOtpSent) {
                return buildForgetPassResponse(0, "Verification code sent to your email successfully.");
            } else {
                return buildForgetPassResponse(1, "Error sending otp!");
            }
        } else {
            return buildForgetPassResponse(1, "Email is not found!");
        }
    }

    private boolean sendVerificationCode(Integer userId, String email) {
        String otp = generateVerificationCode();
        Boolean isOtpSaved = otpService.saveOtp(userId, otp);

        if (isOtpSaved) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Password Reset Verification Code");
            message.setText("Your verification code is: " + otp);
            mailSender.send(message);
            return true;
        } else {
            return false;
        }
    }

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

    private Status buildForgetPassResponse(int statusCode, String message) {
        return Status.builder()
                .statusCode(statusCode)
                .statusMessage(message)
                .build();
    }
}