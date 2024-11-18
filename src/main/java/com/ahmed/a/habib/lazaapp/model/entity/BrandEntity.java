package com.ahmed.a.habib.lazaapp.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "brands")
public class BrandEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Integer brandId;

    @Column(name = "brand_name", nullable = false)
    private String brandName;

    @JsonBackReference
    @OneToMany(mappedBy = "brandEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductEntity> products;
}