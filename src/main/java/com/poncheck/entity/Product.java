package com.poncheck.entity;

import com.poncheck.dto.request.product.CreateProductRequestDTO;
import com.poncheck.enums.PoncheBase;
import com.poncheck.enums.ProductSize;
import com.poncheck.exception.InsufficientStockException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    public Product(
            String name,
            BigDecimal price,
            String flavor,
            String description,
            ProductSize productSize,
            PoncheBase poncheBase,
            Category category,
            String code,
            Business business
            ) {
        this.name = name;
        this.code = code;
        this.stock = 0;
        this.price = price;
        this.flavor = flavor;
        this.description = description;
        this.productSize = productSize;
        this.poncheBase = poncheBase;
        this.category = category;
        this.business = business;
        this.active = true;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false,  unique = true, length = 100)
    private String code;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(length = 50)
    private String flavor;

    @Column(length = 100)
    private String description;

    @Column(nullable = false)
    private Boolean active = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "ponche_base")
    private PoncheBase poncheBase;

    @Enumerated(EnumType.STRING)
    private ProductSize productSize;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    Business business;


    public void updateData(
            String name,
            String code,
            BigDecimal price,
            String flavor,
            String description,
            PoncheBase poncheBase,
            ProductSize productSize,
            Category category
    ) {
        if(name != null){
            this.name = name;
        }
        if(code != null){
            this.code = code;
        }
        if(price != null){
            this.price = price;
        }
        if(flavor != null){
            this.flavor = flavor;
        }
        if(description != null){
            this.description = description;
        }
        if(poncheBase != null){
            this.poncheBase = poncheBase;
        }
        if(productSize != null){
            this.productSize = productSize;
        }
        if(category != null){
            this.category = category;
        }
    }

    public void increaseStock(int quantity){
        this.stock += quantity;
    }

    public void decreaseStock(int quantity){
        if(this.stock < quantity){
            throw new InsufficientStockException("Insufficient stock", this.id);
        }
        this.stock -= quantity;
    }

    public void updateActive(Boolean active){
        if(active != null){
            this.active = active;
        }
    }
}

