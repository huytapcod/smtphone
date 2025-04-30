package com.hqshop.ecommerce.multivendor1.exception;

import lombok.Getter;

@Getter
public enum Errorcode {
    USER_NOT_FOUND("User not found",1000),
    INVALID_KEY("Invalid message key", 1001),
    USERNAME_INVALITD("Usernma must be at alearts 3 charateds", 1003),
    USER_EXISTED("User existed", 1002),
    PASSWORD_INVALITD("Password must be at least 8 characters", 1004),
    USER_NOT_EXISTED("User does not exist", 1005),
    UNAUTHENTICATED("Unthenticated", 1006),
    PRODUCT_NOT_FOUND("Product not found", 1007),
    CANT_FIND_CARTITEM("Can't find cartItem", 1008),
    CANT_UPDATE_CART_ITEM("Can't update cartItem", 1009),
    ACCOUNT_NOT_FOUND("Account not found", 1010),
    ORDER_NOT_FOUND("Order not found", 1011),
    CANCEL_ORDER_NOT_FOUND("Cancel Order not found", 1012),
    ORDER_ITEM_NOT_EXIT("Order item not exit...", 1013),
    PAYMENT_NOT_FOUND("Payment order not found", 1014),
    REVIEW_NOT_FOUND("Review not found", 1015),
    REVIEW_CANT_DELETE("Review cant delete", 1016),
    COUPON_NOT_VALID("Coupon not valid", 1017),
    COUPON_ALREADY_USED("Coupon already used", 1018),
    COUPON_MIN_VALUE_ERROR("Coupon oder less than min order value", 1019),
    COUPON_NOT_FOUND("Coupon not found", 1020),
    CATEGORY_NOT_FOUND("Category not found", 1021),


    ;

    private int code;
    private String message;

    Errorcode(String message, int code) {
        this.message = message;
        this.code = code;
    }



}
