package com.miu.onlinemarketplace.service.domain.product;

import com.miu.onlinemarketplace.common.dto.FileDto;
import com.miu.onlinemarketplace.common.dto.ProductDto;
import com.miu.onlinemarketplace.common.dto.ProductResponseDto;
import com.miu.onlinemarketplace.common.dto.VendorDto;
import com.miu.onlinemarketplace.entities.*;
import com.miu.onlinemarketplace.exception.AppSecurityException;
import com.miu.onlinemarketplace.exception.DataNotFoundException;
import com.miu.onlinemarketplace.repository.ProductCategoryRepository;
import com.miu.onlinemarketplace.repository.ProductRepository;
import com.miu.onlinemarketplace.repository.ProductTempRepository;
import com.miu.onlinemarketplace.repository.VendorRepository;
import com.miu.onlinemarketplace.security.AppSecurityUtils;
import com.miu.onlinemarketplace.security.models.EnumRole;
import com.miu.onlinemarketplace.service.domain.product.dtos.ProductRequestDto;
import com.miu.onlinemarketplace.service.file.FileService;
import com.miu.onlinemarketplace.service.generic.dtos.GenericFilterRequestDTO;
import com.miu.onlinemarketplace.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final ProductTempRepository productTempRepository;
    private final ProductCategoryRepository productCategoryRepository;

    private final VendorRepository vendorRepository;

    private final FileService fileService;

    public ProductServiceImpl(ModelMapper modelMapper, ProductRepository productRepository, ProductTempRepository productTempRepository, ProductCategoryRepository productCategoryRepository, VendorRepository vendorRepository, FileService fileService) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.productTempRepository = productTempRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.vendorRepository = vendorRepository;
        this.fileService = fileService;
    }

    @Override
    public Page<ProductResponseDto> getAllProducts(Pageable pageable, Long categoryId) {
        Page<ProductResponseDto> products;
        if (categoryId != null) {
            products = productRepository.findAllByProductCategory(pageable, categoryId)
                    .map(product -> modelMapper.map(product, ProductResponseDto.class));
        } else {
            products = productRepository.findAll(pageable)
                    .map(product -> modelMapper.map(product, ProductResponseDto.class));
        }
        return products;
    }

    @Override
    public Page<ProductResponseDto> getAllProductsOfVendor(Pageable pageable) {
        Long vendorId = vendorRepository.findByUser_UserId(UserUtils.getCurrentUserId()).orElseThrow(() ->
                new AppSecurityException("User is not a vendor")).getVendorId();

        Page<ProductResponseDto> verifiedProduct = productRepository.findAllByVendor_VendorIdAndIsDeletedIsFalse(pageable, vendorId)
                .map(product -> modelMapper.map(product, ProductResponseDto.class));
        Page<ProductResponseDto> unverifiedProduct = productTempRepository.findAllByVendor_VendorIdAndIsDeletedIsFalse(pageable, vendorId)
                .map(product -> modelMapper.map(product, ProductResponseDto.class));

        List<ProductResponseDto> content = Stream.concat(verifiedProduct.getContent().stream(), unverifiedProduct.getContent().stream())
                .toList();
        Long totalElements = verifiedProduct.getTotalElements() + unverifiedProduct.getTotalElements();
        return new PageImpl<ProductResponseDto>(content, pageable, totalElements);
    }

    @Override
    public Page<ProductResponseDto> getCustomerProducts(Pageable pageable, Long categoryId) {
        Page<ProductResponseDto> products;
        if (categoryId != null) {
            products = productRepository.findByIsDeletedAndIsVerified(pageable, true, false, categoryId)
                    .map(product -> {
                        ProductResponseDto productResponseDto = modelMapper.map(product, ProductResponseDto.class);
                        List<FileDto> images = product.getImages().stream()
                                .map(fileEntity -> modelMapper.map(fileEntity, FileDto.class))
                                .toList();
                        productResponseDto.setImages(images);
                        return productResponseDto;
                    });
        } else {
            products = productRepository.findAll(pageable)
                    .map(product -> modelMapper.map(product, ProductResponseDto.class));
        }
        return products;
    }

    @Override
    public Page<ProductDto> getProductByName(Pageable pageable, String name) {
        Page<ProductDto> productDtos = productRepository.findAllByName(pageable, name)
                .map(product -> modelMapper.map(product, ProductDto.class));
        return productDtos;
    }

    @Override
    public ProductResponseDto getProductByProductId(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow();
        return modelMapper.map(product, ProductResponseDto.class);
    }

    @Override
    public ProductResponseDto getByProductId(Long id) {
        ProductResponseDto productDto = productRepository.findById(id)
                .map(product -> modelMapper.map(product, ProductResponseDto.class)).orElseThrow(() -> {
                    log.error("Product category with id {} not found!!");
                    throw new DataNotFoundException("Product category with id  not found!!");
                });
        return productDto;
    }

    @Override
    public ProductDto createNewProduct(List<MultipartFile> files, ProductDto productDto) {
        Long vendorId = vendorRepository.findByUser_UserId(UserUtils.getCurrentUserId()).orElseThrow(() ->
                new AppSecurityException("User is not a vendor")).getVendorId();
        productDto.setVendorId(vendorId);
        Product product = modelMapper.map(productDto, Product.class);
        List<FileEntity> fileEntities = new ArrayList<>();
        for (var file : files) {
            FileEntity fileEntity = new FileEntity();
            fileEntity.setFileUri(fileService.uploadFiles(file, Arrays.asList("product", vendorId.toString(), productDto.getName().toLowerCase().replace(" ", "-"))));
            fileEntities.add(fileEntity);
        }
        product.setImages(fileEntities);
        product.setIsDeleted(false);
        product.setIsVerified(false);
        product = productRepository.save(product);
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ProductResponseDto verifyProduct(Long productId, boolean isVerified) {
        Product product = productRepository.findById(productId).get();
        product.setIsVerified(isVerified);
        product = productRepository.save(product);
        productTempRepository.deleteById(productId);
        return modelMapper.map(product, ProductResponseDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        Product product = productRepository.findById(productDto.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setQuantity(productDto.getQuantity());
        product.setPrice(productDto.getPrice());
        product.setIsVerified(false);
        product.setIsDeleted(false);
        ProductCategory productCategory = productCategoryRepository.findById(productDto.getCategoryId()).orElseThrow(() -> {
            log.error("Product category with id {} not found!!", productDto.getCategoryId());
            throw new DataNotFoundException("Product category with id " + productDto.getCategoryId() + " not found!!");
        });
        product.setProductCategory(productCategory);

        ProductTemp productTemp = modelMapper.map(product, ProductTemp.class);
        return modelMapper.map(productTempRepository.save(productTemp), ProductDto.class);
    }

    @Override
    public Boolean deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        product.setIsDeleted(true);
        productRepository.save(product);
        return true;
    }

    @Override
    public Page<ProductResponseDto> filterProductData(GenericFilterRequestDTO<ProductRequestDto> genericFilterRequest, Pageable pageable) {
        EnumRole currentUserRole = AppSecurityUtils.getCurrentUserRole()
                .orElseThrow(() -> new AppSecurityException("The table filter not available for guest users"));
        Long currentUserId = AppSecurityUtils.getCurrentUserId()
                .orElseThrow(() -> new AppSecurityException("The table filter not available for guest users"));
        if (currentUserRole.equals(EnumRole.ROLE_USER)) {
            throw new AppSecurityException("The table filter not available for normal users");
        }
        if (currentUserRole.equals(EnumRole.ROLE_VENDOR)) {
            if (genericFilterRequest.getDataFilter() == null) {
                genericFilterRequest.setDataFilter(new ProductRequestDto());
            }
            ProductRequestDto productRequestDto = new ProductRequestDto();
            Vendor vendor = vendorRepository.findByUser_UserId(currentUserId)
                    .orElseThrow(() -> new DataNotFoundException("Linked vendor with given Id, not found"));
            VendorDto vendorDto = new VendorDto();
            vendorDto.setVendorId(vendor.getVendorId());
            productRequestDto.setVendor(vendorDto);
        }
        Specification<Product> specification = Specification
                .where(ProductSearchSpecification.processDynamicProductFilter(genericFilterRequest))
                .and(Specification.where(ProductSearchSpecification.getProductByVendor(genericFilterRequest)))
                .and(Specification.where(ProductSearchSpecification.getProductByCategory(genericFilterRequest)));
        Specification<ProductTemp> specificationTemp = Specification
                .where(ProductTempSearchSpecification.processDynamicProductTempFilter(genericFilterRequest))
                .and(Specification.where(ProductTempSearchSpecification.getProductByVendor(genericFilterRequest)))
                .and(Specification.where(ProductTempSearchSpecification.getProductByCategory(genericFilterRequest)));

        Page<ProductResponseDto> verifiedFilteredProducts = productRepository.findAll(specification, pageable).map(product ->
                modelMapper.map(product, ProductResponseDto.class));
        Page<ProductResponseDto> unVerifiedFilteredProducts = productTempRepository.findAll(specificationTemp, pageable).map(product ->
                modelMapper.map(product, ProductResponseDto.class));

        List<ProductResponseDto> content = Stream
                .concat(verifiedFilteredProducts.getContent().stream(), unVerifiedFilteredProducts.getContent().stream())
                .toList();
        Long totalElements = verifiedFilteredProducts.getTotalElements() + unVerifiedFilteredProducts.getTotalElements();
        return new PageImpl<ProductResponseDto>(content, pageable, totalElements);
    }
}
