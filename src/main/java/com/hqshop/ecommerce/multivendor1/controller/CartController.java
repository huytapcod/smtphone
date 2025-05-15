package com.hqshop.ecommerce.multivendor1.controller;

import com.hqshop.ecommerce.multivendor1.dto.request.AddItemToCart;
import com.hqshop.ecommerce.multivendor1.dto.request.ApiResponse;
import com.hqshop.ecommerce.multivendor1.entity.*;
import com.hqshop.ecommerce.multivendor1.exception.AppException;
import com.hqshop.ecommerce.multivendor1.exception.Errorcode;
import com.hqshop.ecommerce.multivendor1.repository.UserRepository;
import com.hqshop.ecommerce.multivendor1.service.CartItemService;
import com.hqshop.ecommerce.multivendor1.service.CartService;
import com.hqshop.ecommerce.multivendor1.service.ProductService;
import com.hqshop.ecommerce.multivendor1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final CartItemService cartItemService;
    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;
    private final UserRepository userRepository;

    @GetMapping("/test")
    public String test() {
        return "Test endpoint is working";
    }
    @GetMapping
    public ResponseEntity<Cart> findUserCartHandler() throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByName(username).orElseThrow(()->
                new AppException(Errorcode.USER_NOT_FOUND));
        Cart cart = cartService.findUserCart(user);

        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddItemToCart req) throws Exception {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByName(username).orElseThrow(()->
                new AppException(Errorcode.USER_NOT_FOUND));

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Product product = productService.findProductById(req.getProductId());
        CartItem item = cartService.addCartItem(user,
                product,
                req.getSize(),
                req.getQuantity());
        ApiResponse<Cart> response = new ApiResponse<>();
        response.setMessage(item.toString());

        return new ResponseEntity<>(item, HttpStatus.ACCEPTED);

    }
    @DeleteMapping("item/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItemHandle(@PathVariable Long cartItemId) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByName(username).orElseThrow(()->
                new AppException(Errorcode.USER_NOT_FOUND));

        cartItemService.deleteCartItem(user.getId(), cartItemId);
        ApiResponse<Cart> response = new ApiResponse<>();
        response.setMessage("Cart item deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
    @PutMapping("/item/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItemHandle(
            @PathVariable Long cartItemId,
            @RequestBody CartItem cartItem) throws Exception {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByName(username).orElseThrow(()->
                new AppException(Errorcode.USER_NOT_FOUND));
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        CartItem updateCartItem = null;
        if (cartItem.getQuantity() <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CartItem updatedCartItem = cartItemService.UpdateCartItem(user.getId(), cartItemId, cartItem);

        return new ResponseEntity<>(updatedCartItem, HttpStatus.OK);
    }


}
