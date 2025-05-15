package com.hqshop.ecommerce.multivendor1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

import java.util.UUID;
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    String name;
    String password;
    String email;
    String phone;
    @OneToMany
    private Set<Address> addresses = new HashSet<>();
    @ManyToMany
    @JsonIgnore
    Set<Coupon> userCoupons = new HashSet<>();

    Set<String> roles;
//    @OneToOne
//    @JoinColumn(name = "account_id", nullable = false)
//    private Account account;

}
