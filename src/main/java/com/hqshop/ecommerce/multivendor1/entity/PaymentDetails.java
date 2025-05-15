package com.hqshop.ecommerce.multivendor1.entity;

import lombok.Data;

@Data
public class PaymentDetails {
    private String paymentId;
    private String razorpayPaymentLinkId;
    private String razorpayPaymenLinkReferenceId;
    private String razorpayPaymenPaymentLinkStatus;
    private String razorpayPaymenPaymentIdZWSP;

}
