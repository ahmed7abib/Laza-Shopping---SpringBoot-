package com.ahmed.a.habib.lazaapp.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "otp")
@Entity
public class OTPEntity {

    @Id
    @Column(name = "otp_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer otpID;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "otp", unique = true)
    private String otp;
}
