package com.hqshop.ecommerce.multivendor1.service;

import com.hqshop.ecommerce.multivendor1.entity.HomeCategory;
import com.hqshop.ecommerce.multivendor1.exception.AppException;
import com.hqshop.ecommerce.multivendor1.exception.Errorcode;
import com.hqshop.ecommerce.multivendor1.repository.HomeCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeCategoryService {
    private final HomeCategoryRepository homeCategoryRepository;

    public HomeCategory createHomeCategory(HomeCategory homeCategory) {
        return homeCategoryRepository.save(homeCategory);
    }
    public List<HomeCategory> createCategories(List<HomeCategory> homeCategories) {
        if(homeCategoryRepository.findAll().isEmpty()){
            return homeCategoryRepository.saveAll(homeCategories);
        }
        return homeCategoryRepository.findAll();
    }
    public HomeCategory updateHomeCategory(HomeCategory category, Long id) {
        HomeCategory existingHomeCategory = homeCategoryRepository.findById(id)
                .orElseThrow(()-> new AppException(Errorcode.CATEGORY_NOT_FOUND));
        if(category.getImage() != null){
            existingHomeCategory.setImage(category.getImage());
        }
        if(category.getCategoryId() != null){
            existingHomeCategory.setCategoryId(category.getCategoryId());
        }
        return homeCategoryRepository.save(existingHomeCategory);
    }
    public List<HomeCategory> getAllHomeCategories() {
        return homeCategoryRepository.findAll();
    }

}
