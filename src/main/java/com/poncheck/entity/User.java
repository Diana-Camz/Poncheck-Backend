package com.poncheck.entity;

import com.poncheck.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    public User(
            String name,
            String username,
            String password,
            Role role
    ){
      this.name = name;
      this.username = username;
      this.password = password;
      this.role = role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long Id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private Boolean active = true;

    public void updateUser(
            String name,
            String username,
            Role role
    ){
      if(name != null){
          this.name = name;
      }
      if(username != null){
          this.username = username;
      }
      if(role != null){
          this.role = role;
      }
    }

    public void inactiveUser(Boolean active){
        if(active != null){
            this.active = active;
        }
    }
}
