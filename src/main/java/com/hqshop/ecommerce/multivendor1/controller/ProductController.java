package com.hqshop.ecommerce.multivendor1.controller;

import com.hqshop.ecommerce.multivendor1.dto.request.ApiResponse;
import com.hqshop.ecommerce.multivendor1.dto.request.CreateProductRequest;
import com.hqshop.ecommerce.multivendor1.entity.Product;
import com.hqshop.ecommerce.multivendor1.exception.ProductException;
import com.hqshop.ecommerce.multivendor1.repository.ProductRepository;
import com.hqshop.ecommerce.multivendor1.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody CreateProductRequest createProductRequest) throws Exception {
        Product product = productService.createProduct(createProductRequest);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(
            @PathVariable Long productId)
            throws ProductException {
        Product product = productService.findProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam (required = false) String query) {
        List<Product> products = productService.searchProduct(query);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) throws ProductException {
        productService.deleteProduct(productId);
        ApiResponse res = new ApiResponse();
        res.setMessage("Sản phẩm đã được xóa thành công");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) Integer minDiscount,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String stock,
            @RequestParam(defaultValue = "0") Integer pageNumber) {
        System.out.println("color p ----"+pageNumber);
        return new ResponseEntity<>(
                productService.getAllProduct(category, brand,
                        color,size,minPrice,maxPrice,
                        minDiscount,sort,stock,
                        pageNumber), HttpStatus.OK);
    }



}