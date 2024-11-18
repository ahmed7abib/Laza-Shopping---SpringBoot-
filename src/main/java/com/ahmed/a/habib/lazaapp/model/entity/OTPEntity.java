package com.ahmed.a.habib.lazaapp.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "otp")
@Entity
public class OTPEntity extends BaseEntity {

    @Id
    @Column(name = "otp_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer otpID;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "otp", unique = true)
    private String otp;

    @Column(name = "status")
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private UserEntity user;
}