package com.hqshop.ecommerce.multivendor1.repository;

import com.hqshop.ecommerce.multivendor1.entity.Cart;
import com.hqshop.ecommerce.multivendor1.entity.CartItem;
import com.hqshop.ecommerce.multivendor1.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartAndProductAndSize(Cart cart, Product product, String size);
    List<CartItem> findByProductId(Long productId);

}
