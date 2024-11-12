package com.ahmed.a.habib.lazaapp.service.auth.impl;

import com.ahmed.a.habib.lazaapp.model.entity.OTPEntity;
import com.ahmed.a.habib.lazaapp.model.entity.UserEntity;
import com.ahmed.a.habib.lazaapp.model.response.Status;
import com.ahmed.a.habib.lazaapp.repository.OTPRepository;
import com.ahmed.a.habib.lazaapp.repository.UserRepository;
import com.ahmed.a.habib.lazaapp.service.auth.EmailService;
import com.ahmed.a.habib.lazaapp.service.auth.OTPService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.ahmed.a.habib.lazaapp.utils.Utils.generateVerificationCode;

@Service
@RequiredArgsConstructor
public class OTPServiceImpl implements OTPService {

    private final EmailService emailService;
    private final OTPRepository otpRepository;
    private final UserRepository userRepository;

    @Override
    public OTPEntity saveOtp(int userId, String otp, boolean status) {
        return otpRepository.save(OTPEntity.builder()
                .userId(userId)
                .otp(otp)
                .status(status)
                .build()
        );
    }

    @Override
    public void updateOtpStatus(int otpId, boolean status) {
        otpRepository.findByOtpID(otpId).ifPresent(entity -> otpRepository.save(OTPEntity.builder()
                        .otpID(otpId)
                        .userId(entity.getUserId())
                        .otp(entity.getOtp())
                        .status(status)
                        .build()
                )
        );
    }

    @Override
    public Status confirmOtp(String email, String otp) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);

        if (userEntity.isPresent()) {
            int userId = userEntity.get().getUserId();
            Optional<OTPEntity> otpEntity = otpRepository.findByOtpAndUserId(otp, userId);

            boolean isConfirmed = otpEntity.isPresent() && otpEntity.get().isStatus();

            if (isConfirmed) {
                updateOtpStatus(otpEntity.get().getOtpID(), false);
                return Status.buildStatusResponse(0, "Confirmed otp successfully");
            } else {
                return Status.buildStatusResponse(1, "Invalid Otp");
            }
        } else {
            return Status.buildStatusResponse(1, "User is Not Found");
        }
    }

    @Override
    public Status sendOtp(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);

        if (user.isPresent()) {

            int userId = user.get().getUserId();
            markAllAsInValid(userId);

            String otp = generateVerificationCode();
            OTPEntity otpEntity = saveOtp(userId, otp, true);

            if (otpEntity != null) {
                boolean emailSent = sendEmail(otp, email);
                if (emailSent) {
                    return Status.buildStatusResponse(0, "Verification code sent to your email successfully.");
                } else {
                    return Status.buildStatusResponse(1, "Error sending otp!");
                }
            } else {
                return Status.buildStatusResponse(1, "Error sending otp!");
            }
        } else {
            return Status.buildStatusResponse(1, "Email is not found!");
        }
    }

    private void markAllAsInValid(int userId) {
        Optional<List<OTPEntity>> entities = otpRepository.findByUserId(userId);
        entities.ifPresent(otpEntities -> otpEntities.forEach(it -> updateOtpStatus(it.getOtpID(), false)));
    }

    private boolean sendEmail(String otp, String email) {
        return emailService.sendEmail(email, "Password Reset Verification Code", "Your verification code is: " + otp);
    }
}