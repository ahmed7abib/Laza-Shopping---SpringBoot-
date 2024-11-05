package com.ahmed.a.habib.lazaapp.service.auth.impl;

import com.ahmed.a.habib.lazaapp.model.entity.OTPEntity;
import com.ahmed.a.habib.lazaapp.repository.OTPRepository;
import com.ahmed.a.habib.lazaapp.service.auth.OTPService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OTPServiceImpl implements OTPService {

    private final OTPRepository otpRepository;

    @Override
    public Boolean saveOtp(Integer userId, String otp) {
        otpRepository.save(OTPEntity.builder()
                .userId(userId)
                .otp(otp)
                .build()
        );
        return otpRepository.findByOtp(otp).isPresent();
    }
}