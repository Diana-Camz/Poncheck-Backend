package com.poncheck.entity;

import com.poncheck.enums.CashRegisterStatus;
import com.poncheck.enums.TypeCashMovement;
import com.poncheck.enums.TypeInventoryMovement;
import com.poncheck.exception.InvalidCashMovementException;
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
        User user,
        Sales sale,
        CancelledSale cancelledSale,
        CashRegister cashRegister,
        String description,
        Business business
    ){
        this.typeCashMovement = typeCashMovement;
        this.amount = amount;
        this.user = user;
        this.sale = sale;
        this.cancelledSale = cancelledSale;
        this.cashRegister = cashRegister;
        this.description = description;
        this.business = business;
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

    @Column(nullable = false, length = 200)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    Business business;

    public void updateMovement(String description, BigDecimal amount){
        if(description != null){
            this.description = description;
        }
        if(amount == null){
            return;
        }
        if(this.cashRegister.getStatus() == CashRegisterStatus.CLOSED){
            throw new InvalidCashMovementException("INVALID_CASH_MOVEMENT", "Cash movement cannot be edited when cash register is closed");
        }
        if(!this.typeCashMovement.isManualAllowed()){
            throw new InvalidCashMovementException("INVALID_CASH_MOVEMENT", "Movements of type Sale or Refund cannot be edited manually");
        }

        this.amount = amount;
    }
}
