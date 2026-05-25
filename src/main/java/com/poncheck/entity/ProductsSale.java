package com.poncheck.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "products_sale")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductsSale {

    public ProductsSale(
            Integer quantity,
            BigDecimal unitPrice,
            BigDecimal subtotal,
            Product product,
            Sales sale
    ){
            this.quantity = quantity;
            this.unitPrice = unitPrice;
            this.subtotal = subtotal;
            this.product = product;
            this.sale = sale;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sales sale;
}
