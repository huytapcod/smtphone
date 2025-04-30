package com.hqshop.ecommerce.multivendor1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="name",columnDefinition = "varchar(15)",nullable = false)
    private String name;
    @Override
    public String toString() {
        return name;
    }

}
