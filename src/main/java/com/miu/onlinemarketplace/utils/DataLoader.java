package com.miu.onlinemarketplace.utils;

import com.miu.onlinemarketplace.entities.FileEntity;
import com.miu.onlinemarketplace.entities.Product;
import com.miu.onlinemarketplace.repository.FileEntityRepository;
import com.miu.onlinemarketplace.repository.ProductRepository;
import com.miu.onlinemarketplace.service.file.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Component
@Slf4j
public class DataLoader implements ApplicationListener<ApplicationReadyEvent> {

    private final DataSource dataSource;
    private final FileService fileService;
    private final ProductRepository productRepository;
    private final FileEntityRepository fileEntityRepository;

    public DataLoader(DataSource dataSource, FileService fileService, ProductRepository productRepository, FileEntityRepository fileEntityRepository) {
        this.dataSource = dataSource;
        this.fileService = fileService;
        this.productRepository = productRepository;
        this.fileEntityRepository = fileEntityRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Resource initData = new ClassPathResource("data.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initData);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
        this.uploadImagesToProducts();
    }

    public void uploadImagesToProducts() {
        boolean populateProductImages = true;
        if (!populateProductImages) {
            log.info("File data's already present, Not populating images");
            return;
        }
        HashMap<Long, List<String>> prodImgMap = new HashMap<>();

        prodImgMap.put(1L, Arrays.asList("Mac_01.jpg"));
        prodImgMap.put(2L, Arrays.asList("Java_01.jpeg"));
        prodImgMap.put(3L, Arrays.asList("Cooker_02.jpg", "Stainless_01.jpeg"));
        prodImgMap.put(4L, Arrays.asList("xiaomi_01.png"));
        prodImgMap.put(5L, Arrays.asList("qqqwomens.png"));
        prodImgMap.put(6L, Arrays.asList("SamsungGalaxy_01.png", "s22ultra_01.png"));
        prodImgMap.put(7L, Arrays.asList("LeanStartup_01.jpeg"));
        prodImgMap.put(8L, Arrays.asList("basics10nonstick.jpeg"));
        prodImgMap.put(9L, Arrays.asList("canon.png"));
        prodImgMap.put(10L, Arrays.asList("mensshirt_01.png"));
        prodImgMap.put(11L, Arrays.asList("s22ultra_01.png"));
        prodImgMap.put(12L, Arrays.asList("bossheadphone_01.png"));
        prodImgMap.put(13L, Arrays.asList("basics10nonstick.jpeg"));
        prodImgMap.put(14L, Arrays.asList("sonyplaystation_01.png"));
        prodImgMap.put(15L, Arrays.asList("samsung55tv_01.png"));

        try {
            Set<Map.Entry<Long, List<String>>> entries = prodImgMap.entrySet();
            for (Map.Entry<Long, List<String>> entry : entries) {
                List<FileEntity> fileEntityList = new ArrayList<>();
                Optional<Product> productOptional = productRepository.findById(entry.getKey());
                if (productOptional.isPresent()) {
                    Product product = productOptional.get();

                    // Saving images for the product
                    for (String imageName : entry.getValue()) {
                        ClassPathResource resource = new ClassPathResource("images/" + imageName);
                        File file = resource.getFile();
                        if (file.isFile()) {
                            String imageUrl = fileService.uploadImagesFromClassPathResource(resource);
                            FileEntity fileEntity = new FileEntity();
                            fileEntity.setFileUri(imageUrl);
                            fileEntityList.add(fileEntity);
                        }
                    }
                    product.setImages(fileEntityList);
                    productRepository.save(product);
                }

            }
        } catch (IOException e) {
//            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }


}
