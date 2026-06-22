package com.poncheck.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "cancelled_sales")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CancelledSale {

    public CancelledSale(
            Sales sale,
            User user,
            Business business,
            String reason
    ){
        this.sale = sale;
        this.user = user;
        this.reason = reason;
        this.business = business;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cancellation")
    private Long id;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime date;
    private String reason;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sales sale;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    Business business;
}
