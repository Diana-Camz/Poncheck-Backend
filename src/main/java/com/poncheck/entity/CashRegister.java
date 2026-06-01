package com.poncheck.entity;

import com.poncheck.enums.CashRegisterStatus;
import com.poncheck.exception.InvalidCashMovementException;
import com.poncheck.exception.InvalidCashRegisterException;
import com.poncheck.exception.InvalidSaleStateException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.grammars.hql.HqlParser;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

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
            String description,
            Business business
    ){
        this.openingAmount = openingAmount;
        this.expectedAmount = openingAmount;
        this.openedBy = openBy;
        this.description = description;
        this.status = CashRegisterStatus.OPEN;
        this.business = business;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cr")
    private Long id;

    @Column(name = "opening_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal openingAmount;

    @Column(name = "expected_amount", precision = 10, scale = 2)
    private BigDecimal expectedAmount = BigDecimal.ZERO;

    @Column(name = "real_amount", precision = 10, scale = 2)
    private BigDecimal realAmount;

    @Column(precision = 10, scale = 2)
    private BigDecimal difference = BigDecimal.ZERO;

    @CreationTimestamp
    @Column(name = "opened_at", nullable = false, updatable = false)
    private LocalDateTime openedAt;
    @Column(name = "closed_at")
    private LocalDateTime closedAt;
    private String description;

    @Column(nullable = false, name = "status")
    @Enumerated(EnumType.STRING)
    private CashRegisterStatus status;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "opened_by", nullable = false)
    private User openedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "closed_by")
    private User closedBy;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    Business business;

    public void openRegister(){
            this.status = CashRegisterStatus.OPEN;
    }

    public void closeRegister(){
        if(this.status == CashRegisterStatus.CLOSED){
            throw new InvalidCashRegisterException("Cash Register already closed");
        }
            this.status = CashRegisterStatus.CLOSED;
            this.closedAt = LocalDateTime.now();
    }

    public void calculateDifference(){
        this.difference = (this.expectedAmount).subtract(this.realAmount);
    }

    public void increaseExpectedAmount(BigDecimal amount){
        this.expectedAmount = (this.expectedAmount).add(amount);
    }

    public void decreaseExpectedAmount(BigDecimal amount){
        if(this.expectedAmount.compareTo(amount) < 0){
            throw new InvalidCashMovementException("Insufficient expected amount");
        }
        this.expectedAmount = (this.expectedAmount).subtract(amount);
    }

    public void updateRegister(String description, BigDecimal realAmount){
        if(description != null){
            this.description = description;
        }

        if(realAmount != null){
            if(this.status == CashRegisterStatus.CLOSED){
                throw new InvalidCashRegisterException("Real amount cannot be edited when cash register is closed");
            }else{
                this.realAmount = realAmount;
            }
        }
    }
}
