package com.hqshop.ecommerce.multivendor1.dto.request;

import com.hqshop.ecommerce.multivendor1.entity.Product;
import lombok.Data;

@Data
public class AddItemToCart {
    private String size;
    private int quantity;
    private Long productId;
    private String color;
}
