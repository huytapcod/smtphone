package com.hqshop.ecommerce.multivendor1.service;

import com.hqshop.ecommerce.multivendor1.entity.Home;
import com.hqshop.ecommerce.multivendor1.entity.HomeCategory;
import com.hqshop.ecommerce.multivendor1.enums.HomeCategorySection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeService {
    public Home createHomePageData(List<HomeCategory> allCategories) {
        List<HomeCategory> girdCategories = allCategories.stream()
                .filter(category ->
                        category.getSection() == HomeCategorySection.GIRD)
                .collect(Collectors.toList());

        List<HomeCategory> shopByCategories = allCategories.stream()
                .filter(category ->
                        category.getSection() == HomeCategorySection.SHOPBYCATEGORY)
                .collect(Collectors.toList());

        List<HomeCategory> electricCategories = allCategories.stream()
                .filter(category ->
                        category.getSection() == HomeCategorySection.ELECTRICCATEGORY)
                .collect(Collectors.toList());

        List<HomeCategory> dienthoaiCategories = allCategories.stream()
                .filter(category ->
                        category.getSection() == HomeCategorySection.DIENTHOAI)
                .collect(Collectors.toList());

        Home home = new Home();
        home.setGird(girdCategories);
        home.setShopByCategory(shopByCategories);
        home.setElectricCategory(electricCategories);
        home.setDienthoai(dienthoaiCategories);
        return home;
    }
}
