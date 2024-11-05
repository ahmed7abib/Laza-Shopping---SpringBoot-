package com.ahmed.a.habib.lazaapp.model.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer userId;

    @NotBlank(message = "User name is unique and required!")
    private String username;

    @NotBlank(message = "password is required!")
    private String password;

    @NotBlank(message = "email is required!")
    @Email(message = "Invalid email!", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;
}