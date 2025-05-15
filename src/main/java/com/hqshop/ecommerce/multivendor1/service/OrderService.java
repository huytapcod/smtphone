package com.hqshop.ecommerce.multivendor1.service;

import com.hqshop.ecommerce.multivendor1.entity.*;
import com.hqshop.ecommerce.multivendor1.enums.OrderStatus;
import com.hqshop.ecommerce.multivendor1.exception.AppException;
import com.hqshop.ecommerce.multivendor1.exception.Errorcode;
import com.hqshop.ecommerce.multivendor1.repository.AddressRepository;
import com.hqshop.ecommerce.multivendor1.repository.OrderItemRepository;
import com.hqshop.ecommerce.multivendor1.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final OrderItemRepository orderItemRepository;

    public Set<Order> createOrder(User user, Address address, Cart cart) {
        if (!user.getAddresses().contains(address)) {
            user.getAddresses().add(address);
        }
        Address address1 = addressRepository.save(address);

        // Tạo đơn hàng mới
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(address1);
        order.setOrderDate(LocalDateTime.now());

        // Tính tổng giá đơn hàng từ cart
        int totalSellingPrice = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItem()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setSize(cartItem.getSize());
            orderItem.setMrpPrice(cartItem.getMrpPrice());
            orderItem.setSellingPrice(cartItem.getSellingPrice());
            orderItem.setUserId(cartItem.getUserId());

            orderItem.setOrder(order); // gán order cho orderItem

            totalSellingPrice += cartItem.getSellingPrice() * cartItem.getQuantity();

            orderItems.add(orderItem);
        }

        // Set thông tin đơn hàng
        order.setOrderItems(orderItems);
        order.setTotalsellingPrice(totalSellingPrice);

        // Lưu đơn hàng và các item
        Order savedOrder = orderRepository.save(order);
        orderItemRepository.saveAll(orderItems); // các OrderItem đã được set Order ở trên

        return Set.of(savedOrder);

    }
    public Order findOrderById(long orderId) {
        return orderRepository.findById(orderId).orElseThrow(()->
                new AppException(Errorcode.ORDER_NOT_FOUND));
    }

    public List<Order> userOrderHistory(long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order updateOrderStatus(Long orderId, OrderStatus orderStatus){

        Order order = findOrderById(orderId);
        order.setOrderStatus(orderStatus);
        return orderRepository.save(order);
    }

    public Order cancelOrder(Long orderId, User user){
        Order order = findOrderById(orderId);
        if(user.getId().equals(order.getUser().getId())){
            throw new RuntimeException("You don't have access to this order");
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);

    }
    public OrderItem findById(Long id) throws AppException {
        return orderItemRepository.findById(id).orElseThrow(()->
                new AppException(Errorcode.ORDER_ITEM_NOT_EXIT));
    }
}
