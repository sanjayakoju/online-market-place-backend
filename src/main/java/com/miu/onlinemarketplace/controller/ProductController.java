package com.miu.onlinemarketplace.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miu.onlinemarketplace.common.dto.ProductDto;
import com.miu.onlinemarketplace.common.dto.ProductResponseDto;
import com.miu.onlinemarketplace.service.domain.product.ProductService;
import com.miu.onlinemarketplace.service.domain.product.dtos.ProductRequestDto;
import com.miu.onlinemarketplace.service.file.FileService;
import com.miu.onlinemarketplace.service.generic.dtos.GenericFilterRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final FileService fileService;

    public ProductController(final ProductService productService, FileService fileService) {
        this.productService = productService;
        this.fileService = fileService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/allProducts")
    public ResponseEntity<?> getAllVerifiedAndUnverifiedProducts(@PageableDefault(page = 0, size = 10, sort = "productId",
            direction = Sort.Direction.DESC) Pageable pageable,
                                            @RequestParam(required = false) Long categoryId
    ) {
        Page<ProductResponseDto> page = productService.getAllProducts(pageable, categoryId);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_VENDOR')")
    @GetMapping("/allProducts/vendors")
    public ResponseEntity<?> getAllVendorProducts(@PageableDefault(page = 0, size = 10, sort = "productId",
            direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<ProductResponseDto> page = productService.getAllProductsOfVendor(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_VENDOR')")
    @PostMapping("/products")
    public ResponseEntity<?> createNewProduct(@RequestParam("files") List<MultipartFile> files,
                                              @RequestParam("product") String product) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ProductDto productDto = productService.createNewProduct(files, mapper.readValue(product, ProductDto.class));
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("/products/verify")
    public ResponseEntity<?> verifyProduct(@RequestParam("productId") Long productId, @RequestParam("isVerified") boolean isVerified) {
        ProductResponseDto productDto = productService.verifyProduct(productId, isVerified);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_VENDOR')")
    @PutMapping("/products")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDto productDto) {
        ProductDto productDto1 = productService.updateProduct(productDto);
        return new ResponseEntity<>(productDto1, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_VENDOR')")
    @DeleteMapping("/products")
    public ResponseEntity<?> deleteProduct(@RequestParam Long productId) {
        Boolean product = productService.deleteProduct(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VENDOR')")
    @PostMapping("/products/filter")
    public ResponseEntity<?> filterProductData(@RequestBody GenericFilterRequestDTO<ProductRequestDto> genericFilterRequest, Pageable pageable) {
        log.info("Product API: Filter user data");
        Page<ProductResponseDto> productPageable = productService.filterProductData(genericFilterRequest, pageable);
        return new ResponseEntity<>(productPageable, HttpStatus.OK);
    }

    /**
     *
     * @param multipartFile
     * @param paths
     * @return : Returns the file uploaded
     */

    @PostMapping("/products/images")
    public ResponseEntity<?> uploadFiles(@RequestParam MultipartFile multipartFile,
                                         @RequestParam("path") List<String> paths){
        String file = fileService.uploadFiles(multipartFile, paths);
        return new ResponseEntity<>(file, HttpStatus.OK);

    }

    /**
     *
     * @param file
     * @return Return the files
     */
    @GetMapping("/products/images")
    public ResponseEntity<?> downloadImages(@RequestParam String file){
        String files = fileService.downloadImage(file);
        return new ResponseEntity<>(files, HttpStatus.OK);
    }
}
