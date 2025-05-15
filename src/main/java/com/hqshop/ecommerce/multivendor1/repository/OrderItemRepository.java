package com.hqshop.ecommerce.multivendor1.repository;

import com.hqshop.ecommerce.multivendor1.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
