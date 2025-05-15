package com.hqshop.ecommerce.multivendor1.controller;

import com.hqshop.ecommerce.multivendor1.entity.Product;
import com.hqshop.ecommerce.multivendor1.entity.User;
import com.hqshop.ecommerce.multivendor1.entity.Wishlist;
import com.hqshop.ecommerce.multivendor1.exception.AppException;
import com.hqshop.ecommerce.multivendor1.exception.Errorcode;
import com.hqshop.ecommerce.multivendor1.repository.ProductRepository;
import com.hqshop.ecommerce.multivendor1.repository.UserRepository;
import com.hqshop.ecommerce.multivendor1.repository.WishlistRepository;
import com.hqshop.ecommerce.multivendor1.service.ProductService;
import com.hqshop.ecommerce.multivendor1.service.UserService;
import com.hqshop.ecommerce.multivendor1.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;
    private final UserRepository userRepository;
    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<Wishlist> getWishlistByUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByName(username).orElseThrow(()->
                new AppException(Errorcode.USER_NOT_FOUND));
        Wishlist wishlist = wishlistService.getWishlistByUserId(user);
        return ResponseEntity.ok(wishlist);

    }
    @PostMapping("/add-product/{productId}")
    public ResponseEntity<Wishlist> addProductToWishlist(
            @PathVariable Long productId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByName(username).orElseThrow(()->
                new AppException(Errorcode.USER_NOT_FOUND));
        Product product = productService.findProductById(productId);
        Wishlist updateWishlist = wishlistService.addProductToWishlist(user, product);
        return ResponseEntity.ok(updateWishlist);
    }
}
