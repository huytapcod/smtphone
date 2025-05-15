package com.hqshop.ecommerce.multivendor1.controller;

import com.hqshop.ecommerce.multivendor1.entity.Home;
import com.hqshop.ecommerce.multivendor1.entity.HomeCategory;
import com.hqshop.ecommerce.multivendor1.service.HomeCategoryService;
import com.hqshop.ecommerce.multivendor1.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeCategoryController {
    private final HomeCategoryService homeCategoryService;
    private final HomeService homeService;

    @PostMapping("/home/category")
    public ResponseEntity<Home> createHomeCategories(@RequestBody List<HomeCategory> homeCategories) {
        List<HomeCategory> categories = homeCategoryService.createCategories(homeCategories);
        Home home = homeService.createHomePageData(categories);

        return new ResponseEntity<>(home, HttpStatus.ACCEPTED);
    }

}
