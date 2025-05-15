package com.hqshop.ecommerce.multivendor1.repository;

import com.hqshop.ecommerce.multivendor1.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>,
        JpaSpecificationExecutor<com.hqshop.ecommerce.multivendor1.entity.Product> {
//    Page<Product> findAll(Specification<Product> specification, Pageable pageable);
//    List<Product> findByName(String name);
    @Query("SELECT p FROM Product p where (:query is null or lower(p.title) "+
            "LIKE lower(concat('%',:query,'%' ) ) )"+
            "or (:query is null or lower(p.category.name)"+
            "LIKE lower(concat('%', :query,'%') ) ) ")
    List<com.hqshop.ecommerce.multivendor1.entity.Product> searchProduct(@Param("query") String query);

//    <T> ScopedValue<T> findProductById(Long id);
}
