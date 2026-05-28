package com.poncheck.entity;

import com.poncheck.enums.TypeCashMovement;
import com.poncheck.enums.TypeInventoryMovement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cash_movement")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class CashMovement {

    public CashMovement(
        TypeCashMovement typeCashMovement,
        BigDecimal amount,
        String description,
        User user,
        Sales sale,
        CancelledSale cancelledSale,
        CashRegister cashRegister
    ){
        this.typeCashMovement = typeCashMovement;
        this.amount = amount;
        this.description = description;
        this.user = user;
        this.sale = sale;
        this.cancelledSale = cancelledSale;
        this.cashRegister = cashRegister;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movement")
    private Long id;

    @Column(nullable = false, name = "type")
    @Enumerated(EnumType.STRING)
    private TypeCashMovement typeCashMovement;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, name = "movement_at")
    private LocalDateTime movementAt;

    @Column(length = 200)
    private String description;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id")
    private Sales sale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cancelled_sale_id")
    private CancelledSale cancelledSale;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cr_id", nullable = false)
    private CashRegister cashRegister;

}
