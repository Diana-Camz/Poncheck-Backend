package com.poncheck.entity;

import com.poncheck.enums.TypeMovement;
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
public class InventoryMovement {

    public InventoryMovement(
            TypeMovement typeMovement,
            int quantity,
            String description,
            User user,
            Product product,
            Sales sale,
            InventoryMovement referenceMovement

    ){
       this.typeMovement = typeMovement;
       this.quantity = quantity;
       this.description = description;
       this.user = user;
       this.product = product;
       this.sale = sale;
       this.referenceMovement = referenceMovement;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movement")
    private Long id;

    @Column(nullable = false, name = "type")
    @Enumerated(EnumType.STRING)
    private TypeMovement typeMovement;

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
    private InventoryMovement referenceMovement;
}
