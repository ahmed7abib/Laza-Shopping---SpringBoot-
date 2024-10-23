package com.ahmed.a.habib.lazaapp.repository;

import com.ahmed.a.habib.lazaapp.model.entity.Role;
import com.ahmed.a.habib.lazaapp.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByRole(Role role);

    Optional<UserEntity> findByEmail(String email);
}
