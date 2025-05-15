package com.hqshop.ecommerce.multivendor1.service;

import com.hqshop.ecommerce.multivendor1.dto.request.CreateProductRequest;
import com.hqshop.ecommerce.multivendor1.entity.CartItem;
import com.hqshop.ecommerce.multivendor1.entity.Category;
import com.hqshop.ecommerce.multivendor1.entity.Product;
import com.hqshop.ecommerce.multivendor1.exception.AppException;
import com.hqshop.ecommerce.multivendor1.exception.Errorcode;
import com.hqshop.ecommerce.multivendor1.repository.CartItemRepository;
import com.hqshop.ecommerce.multivendor1.repository.CategoryRepository;
import com.hqshop.ecommerce.multivendor1.repository.ProductRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public Product createProduct(CreateProductRequest req) {
        Category category1 =categoryRepository.findByCategoryId(req.getCategory());

        if(category1 == null){
            Category category = new Category();
            category.setCategoryId(req.getCategory());
            category.setLevel(1);
            category1 = categoryRepository.save(category);
        }
        Category category2 =categoryRepository.findByCategoryId(req.getCategory2());

        if(category2 == null){
            Category category = new Category();
            category.setCategoryId(req.getCategory2());
            category.setLevel(2);
            category.setParentCategory(category1);
            category2 = categoryRepository.save(category);
        }

        Category category3 =categoryRepository.findByCategoryId(req.getCategory3());
        if(category3 == null){
            Category category = new Category();
            category.setCategoryId(req.getCategory3());
            category.setLevel(3);
            category.setParentCategory(category2);
            category3 = categoryRepository.save(category);
        }
        System.out.println("Category3: " + req.getCategory3());
        int discountPercentage = calculateDiscountPercentage(req.getMrpPrice(),req.getSellingPrice());

        Product product = new Product();
        product.setCategory(category3);
        product.setDescription(req.getDescription());
        product.setCreatedAt(LocalDateTime.now());
        product.setTitle(req.getTitle());
        product.setQuantity(req.getQuantity());
        product.setColor(req.getColor());
        product.setSellingPrice(req.getSellingPrice());
        product.setImages(req.getImages());
        product.setMrpPrice(req.getMrpPrice());
        product.setSize(req.getSize());
        product.setDiscountPercent(discountPercentage);

        return productRepository.save(product);
    }

    private int calculateDiscountPercentage(int mrpPrice, int sellingPrice) {
        if(mrpPrice <=0 ){
            throw new IllegalArgumentException("mrpPrice must be greater than 0");

        }
        double discount = mrpPrice - sellingPrice;
        double discountPercentage = (discount/mrpPrice)*100;

        return (int)discountPercentage;
    }


    public void deleteProduct(long productId) {
        Product product = findProductById(productId);
        List<CartItem> cartItems = cartItemRepository.findByProductId(productId);
        cartItemRepository.deleteAll(cartItems); // Xóa cart item trước
        productRepository.delete(product);

    }

    public Product updateProduct(long productId, Product product) {
        findProductById(productId);
        product.setId(productId);

        return productRepository.save(product);
    }


    public Product findProductById(long productId) {
        return productRepository.findById(productId).orElseThrow(() ->
                new AppException(Errorcode.PRODUCT_NOT_FOUND)
        );

    }

    public List<Product> searchProduct(String query) {

        return productRepository.searchProduct(query);
    }

    public Page<Product> getAllProduct(String category, String brand, String color, String size, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber) {
        Specification<Product> specification = ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(category!=null){
                Join<Product, Category> join = root.join("category");
                predicates.add(criteriaBuilder.equal(join.get("categoryId"), category));
            }
            if(color != null && !color.isEmpty()){
                System.out.println("coler"+color);
                predicates.add(criteriaBuilder.like(root.get("color"), "%"+color+"%"));

            }
            if(size!=null && size.isEmpty()){
                System.out.println("size"+size);
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("size"), size));
            }
            if(minPrice != null ){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("sellingPrice"), minPrice));
            }
            if(maxPrice != null ){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("sellingPrice"), maxPrice));
            }
            if(minDiscount != null ){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("discountPercent"), minDiscount));
            }
            if(stock != null ){
                predicates.add(criteriaBuilder.equal(root.get("stock"), stock));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
        Pageable pageable;
        if(sort != null && sort.isEmpty() ){
            pageable = switch (sort) {
                case "price_low" -> PageRequest.of(pageNumber != null ? pageNumber : 0, 10,
                        Sort.by("sellingPrice").ascending());
                case "price_high" -> PageRequest.of(pageNumber != null ? pageNumber : 0, 10,
                        Sort.by("sellingPrice").descending());
                default -> PageRequest.of(pageNumber != null ? pageNumber : 0, 10,
                        Sort.unsorted());
            };

        }
        else {
            pageable = PageRequest.of(pageNumber!=null ? pageNumber:0 , 10, Sort.unsorted());
        }
        return productRepository.findAll(specification,pageable);

    }

    public List<Product> getProductByCategory(String category) {
        return List.of();
    }

}
