package com.miu.onlinemarketplace.service.file;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    String uploadFiles(MultipartFile file, List<String> path);

    String uploadImagesFromClassPathResource(ClassPathResource resource);

    String downloadImage(String fileName);
}
