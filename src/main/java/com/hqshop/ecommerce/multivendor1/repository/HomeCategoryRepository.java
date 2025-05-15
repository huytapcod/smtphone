package com.hqshop.ecommerce.multivendor1.repository;

import com.hqshop.ecommerce.multivendor1.entity.HomeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeCategoryRepository extends JpaRepository<HomeCategory, Long> {

}
