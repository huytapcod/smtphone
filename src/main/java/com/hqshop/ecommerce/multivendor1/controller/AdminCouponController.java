package com.hqshop.ecommerce.multivendor1.controller;

import com.hqshop.ecommerce.multivendor1.entity.Cart;
import com.hqshop.ecommerce.multivendor1.entity.Coupon;
import com.hqshop.ecommerce.multivendor1.entity.User;
import com.hqshop.ecommerce.multivendor1.exception.AppException;
import com.hqshop.ecommerce.multivendor1.exception.Errorcode;
import com.hqshop.ecommerce.multivendor1.repository.CouponRepository;
import com.hqshop.ecommerce.multivendor1.repository.UserRepository;
import com.hqshop.ecommerce.multivendor1.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons")
public class AdminCouponController {
    private final CouponService couponService;
    private final UserRepository userRepository;

    @PostMapping("/apply")
    public ResponseEntity<Cart> applyCoupon(@RequestParam String apply,
                                            @RequestParam String code,
                                            @RequestParam double orderValue) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByName(username).orElseThrow(()->
                new AppException(Errorcode.USER_NOT_FOUND));
        Cart cart;
        if(apply.equals("true")){
            cart = couponService.applyCoupon(code, orderValue, user);
        }
        else{
            cart = couponService.removeCoupon(code, user);
        }
        return ResponseEntity.ok(cart);
    }
    @PostMapping("/admin/create")
    public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon coupon) {
        Coupon createCoupon = couponService.createCoupon(coupon);
        return ResponseEntity.ok(createCoupon);
    }
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.ok("Coupon delete successfully");
    }

    @GetMapping("/admin/all")
    public ResponseEntity<List<Coupon>> getAllCoupons() {
        List<Coupon> coupons = couponService.findAllCoupons();
        return ResponseEntity.ok(coupons);
    }

}
