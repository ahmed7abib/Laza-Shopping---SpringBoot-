package com.ahmed.a.habib.lazaapp.repository;

import com.ahmed.a.habib.lazaapp.model.entity.OTPEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTPRepository extends JpaRepository<OTPEntity, Integer> {

    Optional<OTPEntity> findByOtp(String otp);
}
