package com.hqshop.ecommerce.multivendor1.entity;

import lombok.Data;

import java.util.List;

@Data
public class Home {

    private List<HomeCategory> gird;
    private List<HomeCategory> shopByCategory;
    private List<HomeCategory> electricCategory;
    private List<HomeCategory> dienthoai;
}
