//package com.hqshop.ecommerce.multivendor1.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//import lombok.experimental.FieldDefaults;
//import java.util.Collection;
//import java.util.Date;
//
//@Entity
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class Account {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @Column(name="username",columnDefinition = "varchar(65)")
//    private String username;
//
//    @Column(name="password",columnDefinition = "varchar(65)")
//    private String password;
//
//    @Column(name="email",nullable = false)
//    private String email;
//
//    @Column(name = "created_at", nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdAt;
//
//    @Column(name="reset_password_token",columnDefinition = "varchar(50)")
//    private String resetPasswordToken;
//
//    @Column(name="registrationToken",columnDefinition = "varchar(50)")
//    private String registrationToken;
//
//    @Column(name="auth_provider",columnDefinition = "varchar(20)",nullable = false)
//    private String auth_provider;
//    @Column(name="status",columnDefinition = "BIT",nullable = false)
//    private boolean status;
//
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "account_role",
//            joinColumns = {@JoinColumn(name = "account_id", referencedColumnName = "account_id")},
//            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")}
//    )
//    private Collection<Role> roles;

//}
