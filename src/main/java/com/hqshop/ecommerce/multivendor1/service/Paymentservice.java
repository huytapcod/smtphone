package com.hqshop.ecommerce.multivendor1.service;

import com.hqshop.ecommerce.multivendor1.entity.Address;
import com.hqshop.ecommerce.multivendor1.entity.Order;
import com.hqshop.ecommerce.multivendor1.entity.PaymentOrder;
import com.hqshop.ecommerce.multivendor1.entity.User;
import com.hqshop.ecommerce.multivendor1.enums.OrderStatus;
import com.hqshop.ecommerce.multivendor1.enums.PaymentOrderStatus;
import com.hqshop.ecommerce.multivendor1.enums.PaymentStatus;
import com.hqshop.ecommerce.multivendor1.exception.AppException;
import com.hqshop.ecommerce.multivendor1.exception.Errorcode;
import com.hqshop.ecommerce.multivendor1.repository.OrderRepository;
import com.hqshop.ecommerce.multivendor1.repository.PaymentOrderRepository;
import com.hqshop.ecommerce.multivendor1.repository.UserRepository;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class Paymentservice {

    private final PaymentOrderRepository paymentOrderRepository;
    private final OrderRepository orderRepository;

    private String apiKey = "apiKey";
    private String apiSecret = "apiSecretKey";

    public PaymentOrder createOrder(User user, Set<Order> orders) {
        Long amount = orders.stream().mapToLong(Order::getTotalsellingPrice).sum();
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setAmount(amount);
        paymentOrder.setUser(user);
        paymentOrder.setOrders(orders);
        return paymentOrderRepository.save(paymentOrder);

    }
    public PaymentOrder getPaymentOrderById(Long orderId) {
        return paymentOrderRepository.findById(orderId).orElseThrow(()
                -> new AppException(Errorcode.PAYMENT_NOT_FOUND));
    }
    public PaymentOrder getPaymentOrderByPaymentId(PaymentOrder paymentOrder) {
        PaymentOrder paymentOrder1 = paymentOrderRepository.findByPaymentLinkId(paymentOrder.getPaymentLinkId());
        if(paymentOrder1 == null) {
            throw new AppException(Errorcode.PAYMENT_NOT_FOUND);
        }
        return paymentOrder1;
    }
    public Boolean ProcessPaymentOrder(PaymentOrder paymentOrder,
                                       String paymentId,
                                       String paymentLinkId) throws RazorpayException {
        if(paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)) {
            RazorpayClient razorpayClient = new RazorpayClient(apiKey, apiSecret);

            Payment payment = razorpayClient.payments.fetch(paymentId);

            String status = payment.get("status");

            if(status.equals("captured")) {
                Set<Order> orders = paymentOrder.getOrders();
                for(Order order : orders) {
                    order.setOrderStatus(OrderStatus.CONFIRMED);
                    orderRepository.save(order);
                }
                paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                paymentOrderRepository.save(paymentOrder);
                return true;
            }
            paymentOrder.setStatus(PaymentOrderStatus.FAILED);
            paymentOrderRepository.save(paymentOrder);
            return false;
        }
        return false;
    }
//    public PaymentLink createPRazorpayPaymentLink(User user, Long amount, Long orderId) {
//
//    }
}
