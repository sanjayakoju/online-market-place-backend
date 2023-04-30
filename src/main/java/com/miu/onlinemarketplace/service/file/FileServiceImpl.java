package com.miu.onlinemarketplace.service.file;

import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;

    public FileServiceImpl(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public String uploadImagesFromClassPathResource(ClassPathResource resource) {
        try {

            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            String fileName = resource.getFile().getName();
            String[] extension = fileName.split("\\.");
            String objectName = pathGenerate(List.of("/products")) + UUID.randomUUID().toString() + "." + extension[extension.length - 1];;
            InputStream inputStream = resource.getInputStream();
            // Upload the image to MinIO
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(inputStream, resource.contentLength(), -1)
//                    .contentType("image/jpeg")
                    .build());
            String downloadUrl = downloadImage(objectName);
            return downloadUrl;
        } catch (Exception e) {
            log.error("Error on uploading file {}, {}", resource.getFilename(), e.getMessage());
//            e.printStackTrace();
        }
        return "http://via.placeholder.com/640x360";
    }


    public String uploadFiles(MultipartFile file, List<String> path) {
        String[] extension = Objects.requireNonNull(file.getContentType()).split("/");
        String objectName = pathGenerate(path) + UUID.randomUUID().toString() + "." + extension[extension.length - 1];
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            ObjectWriteResponse response = minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .build());
            return downloadImage(objectName);
        } catch (Exception e) {
            log.error("Error on uploading file {}, {}", file.getOriginalFilename(), e.getMessage());
        }
        return "http://via.placeholder.com/640x360";
    }

    public String downloadImage(String filePath) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(filePath)
                            .expiry(7, TimeUnit.DAYS)
                            .build());
        } catch (MinioException | NoSuchAlgorithmException | InvalidKeyException | IOException e) {
            log.error("Error on download file from {}, {}", filePath, e.getMessage());
            return null;
        }
    }

    private String pathGenerate(List<String> path) {
        StringBuilder pathBuilder = new StringBuilder();
        for (String pt : path) {
            if (pt != null && !pt.equals("")) pathBuilder.append(pt).append("/");
        }
        return pathBuilder.toString();
    }

}
