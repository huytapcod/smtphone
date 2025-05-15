package com.hqshop.ecommerce.multivendor1.controller;

import com.hqshop.ecommerce.multivendor1.entity.Address;
import com.hqshop.ecommerce.multivendor1.entity.Cart;
import com.hqshop.ecommerce.multivendor1.entity.Order;
import com.hqshop.ecommerce.multivendor1.entity.User;
import com.hqshop.ecommerce.multivendor1.enums.OrderStatus;
import com.hqshop.ecommerce.multivendor1.exception.AppException;
import com.hqshop.ecommerce.multivendor1.exception.Errorcode;
import com.hqshop.ecommerce.multivendor1.repository.UserRepository;
import com.hqshop.ecommerce.multivendor1.service.CartService;
import com.hqshop.ecommerce.multivendor1.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;
    private final UserRepository userRepository;

    // Tạo đơn hàng
    @PostMapping("/create")
    public ResponseEntity<Set<Order>> createOrder(@RequestBody Address address, Principal principal) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByName(username).orElseThrow(()->
                new AppException(Errorcode.USER_NOT_FOUND));
        Cart cart = cartService.findUserCart(user);
        Set<Order> orders = orderService.createOrder(user, address, cart);
        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }

    // Lịch sử đặt hàng của người dùng
    @GetMapping("/my")
    public ResponseEntity<List<Order>> getUserOrders(Principal principal) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByName(username).orElseThrow(()->
                new AppException(Errorcode.USER_NOT_FOUND));
        List<Order> orders = orderService.userOrderHistory(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // Xem chi tiết đơn hàng theo ID
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        Order order = orderService.findOrderById(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    // Huỷ đơn hàng
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long orderId, Principal principal) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByName(username).orElseThrow(()->
                new AppException(Errorcode.USER_NOT_FOUND));
        Order order = orderService.cancelOrder(orderId, user);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    // Cập nhật trạng thái đơn hàng (dành cho admin)
    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId,
                                                   @RequestParam OrderStatus status) {
        Order order = orderService.updateOrderStatus(orderId, status);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
