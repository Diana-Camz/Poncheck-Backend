package com.poncheck.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "business")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Business {

    public Business(
            String name,
            String businessCode,
            String phone,
            String email,
            String address,
            String description,
            String logoUrl,
            User owner

    ){
        this.name = name;
        this.businessCode = businessCode;
        this.active = true;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.description = description;
        this.logoUrl = logoUrl;
        this.owner = owner;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_business")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name="business_code", nullable = false, unique = true)
    private String businessCode;

    @Column(nullable = false)
    private Boolean active;

    private String phone;
    private String email;
    private String address;
    private String description;

    @Column(name = "logo_url")
    private String logoUrl;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    public void updateBusiness(
            String name,
            String phone,
            String email,
            String address,
            String description,
            String logoUrl
    ){
        if(name != null){
            this.name = name;
        }
        if(phone != null){
            this.phone = phone;
        }
        if(email != null){
            this.email = email;
        }
        if(address != null){
            this.address = address;
        }
        if(description != null){
            this.description = description;
        }
        if(logoUrl != null){
            this.logoUrl = logoUrl;
        }

    }

    public void updateBusinessOwner(User owner){
        if(owner != null){
            this.owner = owner;
        }
    }

    public void updateActive(Boolean active){
        if(active != null){
            this.active = active;
        }
    }

}
