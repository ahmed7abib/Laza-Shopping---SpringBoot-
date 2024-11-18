package com.ahmed.a.habib.lazaapp.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "favorite_products")
public class FavoriteProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Integer favoriteId;



    @OneToMany(mappedBy = "favoriteProductEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductEntity> products;
}
