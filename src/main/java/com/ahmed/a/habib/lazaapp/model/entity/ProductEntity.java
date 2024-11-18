package com.ahmed.a.habib.lazaapp.model.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "products")
public class ProductEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "product_description")
    private String productDesc;

    @Column(name = "product_price", nullable = false)
    private double productPrice;

    @Column(name = "product_full_image")
    private String productFullImage;

    @Column(name = "vat", nullable = false)
    private double vat;

    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    @Column(name = "brand_id", nullable = false)
    private Integer brandId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", insertable = false, updatable = false)
    private BrandEntity brandEntity;

    @JsonBackReference
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ReviewEntity> reviews;

    @ElementCollection
    @CollectionTable(name = "product_cuts_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private Set<String> productCutsImages;

    @ElementCollection
    @CollectionTable(name = "available_sizes", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "size")
    private Set<String> availableSizes;

    // Many-to-Many: More than one product can be favorite by more than one user
    @JsonManagedReference
    @ManyToMany(mappedBy = "favoriteProducts")
    private Set<UserEntity> usersList;

    // Many-to-Many: Products in new arrivals
    @JsonManagedReference
    @ManyToMany(mappedBy = "newArrivalProducts")
    private Set<UserEntity> usersWhoAddedToNewArrivals;
}