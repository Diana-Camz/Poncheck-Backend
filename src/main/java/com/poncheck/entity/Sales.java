package com.poncheck.entity;

import com.poncheck.enums.PaymentMethod;
import com.poncheck.enums.SaleStatus;
import com.poncheck.exception.InvalidSaleStateException;
import com.poncheck.exception.SaleAlreadyCancelledException;
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

import static com.poncheck.enums.SaleStatus.CANCELLED;
import static com.poncheck.enums.SaleStatus.COMPLETED;

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
            User user,
            CashRegister cashRegister
    ){
        this.total = total;
        this.paymentMethod = paymentMethod;
        this.description = description;
        this.user = user;
        this.saleStatus = COMPLETED;
        this.cashRegister = cashRegister;
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

    @Column(nullable = false, name = "sale_status")
    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;

    @OneToOne(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private CancelledSale cancelled;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SaleItem> items = new ArrayList<>();

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cr_id", nullable = false)
    private CashRegister cashRegister;

    public void addSaleItem(SaleItem saleItem){
        items.add(saleItem);
        saleItem.setSale(this);
    }
    public void updateSale(
            PaymentMethod paymentMethod,
            String description
    ){
        if(this.saleStatus == CANCELLED){
            throw new SaleAlreadyCancelledException("Cancelled Sales Cannot Be Edited");
        }
        if(paymentMethod != null){
            this.paymentMethod = paymentMethod;
        }
        if(description != null){
            this.description = description;
        }

    }

    public void cancelSale(User user, String reason){
        if(this.saleStatus == CANCELLED){
            throw new InvalidSaleStateException("Sale Already Cancelled");
        }
        this.saleStatus = CANCELLED;
        CancelledSale cancelledSale = new CancelledSale(
                this,
                user,
                reason
        );
        cancelledSale.setSale(this);
        this.cancelled = cancelledSale;
    }

}
