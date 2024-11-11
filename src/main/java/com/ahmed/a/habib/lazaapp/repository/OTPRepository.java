package com.ahmed.a.habib.lazaapp.repository;

import com.ahmed.a.habib.lazaapp.model.entity.OTPEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OTPRepository extends JpaRepository<OTPEntity, Integer> {

    Optional<OTPEntity> findByOtpID(int otpId);

    Optional<List<OTPEntity>> findByUserId(int userId);

    Optional<OTPEntity> findByOtpAndUserId(String otp, int userId);
}