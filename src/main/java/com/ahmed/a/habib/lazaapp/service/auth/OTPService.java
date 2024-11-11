package com.ahmed.a.habib.lazaapp.service.auth;

import com.ahmed.a.habib.lazaapp.model.entity.OTPEntity;
import com.ahmed.a.habib.lazaapp.model.response.Status;

public interface OTPService {

    OTPEntity saveOtp(int userId, String otp, boolean status);

    void updateOtpStatus(int otpId, boolean status);

    Status sendOtp(String email);

    Status confirmOtp(String email, String otp);
}