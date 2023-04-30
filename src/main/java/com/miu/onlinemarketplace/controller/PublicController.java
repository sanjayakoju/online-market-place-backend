package com.miu.onlinemarketplace.controller;

import com.miu.onlinemarketplace.common.dto.ProductCategoryDto;
import com.miu.onlinemarketplace.common.dto.ProductResponseDto;
import com.miu.onlinemarketplace.service.domain.product.ProductCategoryService;
import com.miu.onlinemarketplace.service.domain.product.ProductService;
import com.miu.onlinemarketplace.service.file.FileService;
import com.miu.onlinemarketplace.service.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/public")
public class PublicController {

    private final ProductService productService;
    private ProductCategoryService productCategoryService;
    private OrderService orderService;


    public PublicController(ProductService productService, ProductCategoryService productCategoryService, OrderService orderService) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
        this.orderService = orderService;
    }

    @GetMapping("/products")
    public ResponseEntity<?> getCustomerProducts(@PageableDefault(page = 0, size = 10, sort = "productId",
            direction = Sort.Direction.DESC) Pageable pageable,
                                                 @RequestParam(required = false) Long categoryId
    ) {
        Page<ProductResponseDto> page = productService.getCustomerProducts(pageable, categoryId);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/products/name/{name}")
    public ResponseEntity<?> getProductByName(
            @PageableDefault(page = 0, size = 10, sort = "productId",
                    direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable String name) {
        Page page = productService.getProductByName(pageable, name);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/allProducts/{productId}")
    public ResponseEntity<?> getAllProductId(@PathVariable Long productId) {
        ProductResponseDto productDto = productService.getProductByProductId(productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<?> getByProductId(@PathVariable Long productId) {
        ProductResponseDto productDto = productService.getByProductId(productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    /**
     * Category Service
     *
     * @return : Returns all the categories
     */
    @GetMapping("/category")
    public ResponseEntity<?> GetAllCategory() {
        List<ProductCategoryDto> categories = productCategoryService.getAllCategory();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/top-products-by-category")
    public ResponseEntity<?> getTopPublishedProductsByCategory(@PageableDefault(page = 0, size = 10, sort = "productId",
            direction = Sort.Direction.DESC) Pageable pageable, @RequestParam(required = false) Long categoryId) {
        Page<ProductResponseDto> page = productService.getCustomerProducts(pageable, categoryId);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<?> getOrderItemList(@RequestParam String productCode) {
        return new ResponseEntity<>(orderService.getAllOrderItemsByOrderCode(productCode), HttpStatus.OK);
    }

}
