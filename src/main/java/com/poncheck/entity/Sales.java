package com.poncheck.entity;

import com.poncheck.dto.request.sales.SaleItemRequestDTO;
import com.poncheck.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Sales {

    public Sales (
            BigDecimal total,
            PaymentMethod paymentMethod,
            String description,
            User user
    ){
        this.total = total;
        this.paymentMethod = paymentMethod;
        this.description = description;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sale")
    private Long id;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(nullable = false, name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime date;

    @Column(length = 255)
    private String description;

    private Boolean cancelled = false;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SaleItem> items = new ArrayList<>();

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void addSaleItem(SaleItem saleItem){
        items.add(saleItem);
        saleItem.setSale(this);
    }
    public void updateSale(
            PaymentMethod paymentMethod,
            String description,
            User user
    ){
        if(paymentMethod != null){
            this.paymentMethod = paymentMethod;
        }
        if(description != null){
            this.description = description;
        }
        if(user != null){
            this.user = user;
        }

    }

    public void cancelSale(Boolean cancel){
        if(cancelled != null){
            this.cancelled = cancel;
        }
    }

}
