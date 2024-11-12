package com.ahmed.a.habib.lazaapp.model.response;

import com.ahmed.a.habib.lazaapp.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private Status status;
    private String token;
    private UserDto userDto;

    public static AuthResponse buildAuthResponse(int statusCode, String message, String token, UserDto userDto) {
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
