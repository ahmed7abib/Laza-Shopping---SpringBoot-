package com.ahmed.a.habib.lazaapp.service.auth;

import com.ahmed.a.habib.lazaapp.model.entity.OTPEntity;
import com.ahmed.a.habib.lazaapp.model.response.Status;

public interface OTPService {

    Status sendOtp(String email);

    Status confirmOtp(String email, String otp);

    void updateOtpStatus(int otpId, boolean status);

    OTPEntity saveOtp(int userId, String otp, boolean status);
}