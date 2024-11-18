package com.ahmed.a.habib.lazaapp.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reviews")
public class ReviewEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Integer reviewId;

    @Column(name = "review_text")
    private String reviewText;

    @Column(name = "review_date")
    private String reviewDate;

    @Column(name = "rating")
    private double rating;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "product_id")
    private int productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false, insertable = false)
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, updatable = false, insertable = false)
    private ProductEntity productEntity;
}