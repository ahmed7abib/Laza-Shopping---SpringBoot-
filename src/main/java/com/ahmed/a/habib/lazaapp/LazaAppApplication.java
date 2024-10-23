package com.ahmed.a.habib.lazaapp;

import com.ahmed.a.habib.lazaapp.model.entity.Role;
import com.ahmed.a.habib.lazaapp.model.entity.UserEntity;
import com.ahmed.a.habib.lazaapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@SpringBootApplication
@RequiredArgsConstructor
public class LazaAppApplication implements CommandLineRunner {

    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(LazaAppApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Optional<UserEntity> adminAccount = userRepository.findByRole(Role.ADMIN);

        if (adminAccount.isEmpty()) {
            UserEntity admin = UserEntity.builder()
                    .email("admin@gmail.com")
                    .firstName("admin")
                    .lastName("admin")
                    .role(Role.ADMIN)
                    .password(new BCryptPasswordEncoder().encode("admin"))
                    .build();

            userRepository.save(admin);
        }
    }
}
