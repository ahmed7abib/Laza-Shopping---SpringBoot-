package com.ahmed.a.habib.lazaapp.model.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "new_arrivals")
public class NewArrivalEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "new_arrival_id")
    private Integer newArrivalId;

    @OneToMany(mappedBy = "newArrivalEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductEntity> products;
}