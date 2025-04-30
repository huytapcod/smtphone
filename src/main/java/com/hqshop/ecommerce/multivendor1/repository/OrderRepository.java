package com.hqshop.ecommerce.multivendor1.repository;

import com.hqshop.ecommerce.multivendor1.entity.Address;
import com.hqshop.ecommerce.multivendor1.entity.Order;
import com.hqshop.ecommerce.multivendor1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long user_id);
//    List<Order> findByAddressAndUserId(Address address, Long user_id);

}
