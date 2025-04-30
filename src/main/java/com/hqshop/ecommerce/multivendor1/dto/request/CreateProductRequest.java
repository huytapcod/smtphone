package com.hqshop.ecommerce.multivendor1.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateProductRequest {
    private String title;
    private String description;
    private int mrpPrice;
    private int quantity;
    private int sellingPrice;
    private String color;
    private String category;
    private String category2;
    private String category3;
    private List<String> images;
    private String size;

}
