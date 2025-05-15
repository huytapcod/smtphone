package com.hqshop.ecommerce.multivendor1.service;

import com.hqshop.ecommerce.multivendor1.entity.*;
import com.hqshop.ecommerce.multivendor1.repository.CartItemRepository;
import com.hqshop.ecommerce.multivendor1.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    public CartItem addCartItem(User user, Product product, String size, int quantity) {
        Cart cart = findUserCart(user);

        CartItem isPresent = cartItemRepository.findByCartAndProductAndSize(cart, product, size);
        if (isPresent == null) {
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUserId(user.getId());
            cartItem.setSize(size);

            int totalPrice = quantity*product.getSellingPrice();
            cartItem.setSellingPrice(totalPrice);

            cartItem.setMrpPrice(quantity*product.getMrpPrice());

            cart.getCartItem().add(cartItem);
            cartItem.setCart(cart);

            return cartItemRepository.save(cartItem);
        }
        return isPresent;

    }

    public Cart findUserCart(User user) {
//        Cart cart = cartRepository.findByUserId(user.getId());
        Cart cart = cartRepository.findByUserId(user.getId());

        int totalPrice = 0;
        int totalDiscountePercent = 0;
        int totalItem = 0;
        

        for(CartItem item : cart.getCartItem()) {
            totalPrice += item.getMrpPrice();
            totalDiscountePercent += item.getSellingPrice();
            totalItem += item.getQuantity();
        }
        cart.setTotalMrpPrice(totalPrice);
        cart.setTotalItems(totalItem);
        cart.setTotalSellingPrice(totalDiscountePercent);
        cart.setDiscount(calculateDiscountPercentage(totalPrice, totalDiscountePercent));
        cart.setTotalItems(totalItem);

        return cart;

    }

    private int calculateDiscountPercentage(int mrpPrice, int sellingPrice) {
        if(mrpPrice <=0 ){
            return 0;

        }
        double discount = mrpPrice - sellingPrice;
        double discountPercentage = (discount/mrpPrice)*100;

        return (int)discountPercentage;
    }
}
