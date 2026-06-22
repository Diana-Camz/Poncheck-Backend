package com.poncheck.entity;

import com.poncheck.enums.TypeInventoryMovement;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_movement")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Movement {

    public Movement(
            TypeInventoryMovement typeInventoryMovement,
            int quantity,
            String description,
            User user,
            Product product,
            Sales sale,
            Movement referenceMovement,
            Business business

    ){
       this.typeInventoryMovement = typeInventoryMovement;
       this.quantity = quantity;
       this.description = description;
       this.user = user;
       this.product = product;
       this.sale = sale;
       this.referenceMovement = referenceMovement;
       this.business = business;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movement")
    private Long id;

    @Column(nullable = false, name = "type")
    @Enumerated(EnumType.STRING)
    private TypeInventoryMovement typeInventoryMovement;

    @Column(nullable = false)
    private int quantity;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, name = "movement_at")
    private LocalDateTime movementAt;

    @Column(length = 200)
    private String description;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id")
    private Sales sale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_movement_id")
    private Movement referenceMovement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    Business business;

    public void updateMovement(String description){
        if(description != null){
            this.description = description;
        }
    }
}
