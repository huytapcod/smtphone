package com.hqshop.ecommerce.multivendor1.service;

import com.hqshop.ecommerce.multivendor1.entity.CartItem;
import com.hqshop.ecommerce.multivendor1.entity.User;
import com.hqshop.ecommerce.multivendor1.exception.AppException;
import com.hqshop.ecommerce.multivendor1.exception.Errorcode;
import com.hqshop.ecommerce.multivendor1.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    public CartItem UpdateCartItem(Long userId, Long id, CartItem cartItem) throws Exception {
        CartItem item = findCartItemById(id);

        User cartItemUser = item.getCart().getUser();

        if (cartItemUser.getId().equals(userId)) {
            item.setQuantity(cartItem.getQuantity());
            item.setMrpPrice(item.getQuantity()*item.getProduct().getMrpPrice());
            item.setSellingPrice(item.getQuantity()*item.getProduct().getSellingPrice());
            return cartItemRepository.save(item);
        }
        throw new Exception("User can't update this cartItem");

    }
    public void deleteCartItem(Long userId, Long cartItemId) throws Exception {
        CartItem item = findCartItemById(cartItemId);

        User cartItemUser = item.getCart().getUser();
        if (cartItemUser.getId().equals(userId)) {
            cartItemRepository.delete(item);
        }
        else throw new AppException(Errorcode.CANT_UPDATE_CART_ITEM);
    }

    private CartItem findCartItemById(Long id) {
        return cartItemRepository.findById(id).orElseThrow(()->
                new AppException(Errorcode.CANT_FIND_CARTITEM));
    }
}
