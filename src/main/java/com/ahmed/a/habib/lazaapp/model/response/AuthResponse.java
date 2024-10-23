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
}
