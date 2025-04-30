package com.hqshop.ecommerce.multivendor1.service;

import com.hqshop.ecommerce.multivendor1.entity.Cart;
import com.hqshop.ecommerce.multivendor1.entity.Coupon;
import com.hqshop.ecommerce.multivendor1.entity.User;
import com.hqshop.ecommerce.multivendor1.exception.AppException;
import com.hqshop.ecommerce.multivendor1.exception.Errorcode;
import com.hqshop.ecommerce.multivendor1.repository.CartRepository;
import com.hqshop.ecommerce.multivendor1.repository.CouponRepository;
import com.hqshop.ecommerce.multivendor1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public Cart applyCoupon(String code, double orderValue, User user) {
        Coupon c = couponRepository.findByCode(code);

        Cart cart = cartRepository.findByUserId(user.getId());

        if (c == null) {
            throw new AppException(Errorcode.COUPON_NOT_VALID);
        }
        if(user.getUserCoupons().contains(c)) {
            throw new AppException(Errorcode.COUPON_ALREADY_USED);
        }
        if(orderValue <= c.getMinimumOrderValue()) {
            throw new AppException(Errorcode.COUPON_MIN_VALUE_ERROR, "Minimum order value "+ c.getMinimumOrderValue());
        }

        if(c.isActiv() &&
                LocalDate.now().isAfter(c.getValidityStartDate()) &&
                LocalDate.now().isBefore(c.getValidityEndDate())) {

            user.getUserCoupons().add(c);
            userRepository.save(user);

            double discountPrice = (cart.getTotalSellingPrice()*c.getDiscountPercent())/100;
            cart.setTotalSellingPrice(cart.getTotalSellingPrice()-discountPrice);
            cart.setCouponCode(code);
            cartRepository.save(cart);
            return cart;
        }
        throw new AppException(Errorcode.COUPON_NOT_VALID);
    }
    public Cart removeCoupon(String code, User user) {
        Coupon c = couponRepository.findByCode(code);
        if(c == null) {
            throw new AppException(Errorcode.COUPON_NOT_VALID);
        }
        Cart cart = cartRepository.findByUserId(user.getId());
        double discountPrice = (cart.getTotalSellingPrice()*c.getDiscountPercent())/100;
        cart.setCouponCode(null);

        return cartRepository.save(cart);
    }
    public Coupon findCouponById(Long id) {
        return couponRepository.findById(id).orElseThrow(()->
                new AppException(Errorcode.COUPON_NOT_VALID));

    }
    @PreAuthorize("hasRole('ADMIN')")
    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }
    public List<Coupon> findAllCoupons() {
        return couponRepository.findAll();
    }
    @PreAuthorize("hasRole('ADMIN')")
    public Coupon deleteCoupon(Long id) throws AppException {
        findCouponById(id);
        Coupon coupon = findCouponById(id); // Throws if not found
        couponRepository.deleteById(id);
        return coupon;
    }

}
