package com.poncheck.entity;

import com.poncheck.enums.StoreStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cash_register")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CashRegister {

    public CashRegister(
            BigDecimal openingAmount,
            User openBy,
            String description
    ){
        this.openingAmount = openingAmount;
        this.openBy = openBy;
        this.description = description;
        this.status = StoreStatus.OPEN;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cr")
    private Long id;

    @Column(name = "opening_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal openingAmount;

    @Column(name = "expected_amount", precision = 10, scale = 2)
    private BigDecimal expectedAmount;

    @Column(name = "real_amount", precision = 10, scale = 2)
    private BigDecimal realAmount;

    @Column(precision = 10, scale = 2)
    private BigDecimal difference;

    @CreationTimestamp
    @Column(name = "opened_at", nullable = false, updatable = false)
    private LocalDateTime openedAt;
    @Column(name = "closed_at")
    private LocalDateTime closedAt;
    private String description;

    @Column(nullable = false, name = "status")
    @Enumerated(EnumType.STRING)
    private StoreStatus status;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "opened_by", nullable = false)
    private User openBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "closed_by")
    private User closedBy;

}
