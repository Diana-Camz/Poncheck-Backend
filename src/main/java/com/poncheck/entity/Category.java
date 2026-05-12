package com.poncheck.entity;


import com.poncheck.dto.request.category.CreateCategoryRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "category")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    public Category(CreateCategoryRequestDTO data){
        this.name = data.name();
        this.active = true;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category")
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false)
    private Boolean active = true;

    public void updateCategory(String name){
        if(name != null){
            this.name = name;
        }
    }

    public void updateActive(Boolean active){
        if(active != null){
            this.active = active;
        }
    }

}
