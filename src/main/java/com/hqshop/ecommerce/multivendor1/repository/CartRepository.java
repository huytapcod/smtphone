package com.hqshop.ecommerce.multivendor1.repository;

import com.hqshop.ecommerce.multivendor1.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);
}
