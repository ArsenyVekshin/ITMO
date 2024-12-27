package com.arsenyvekshin.lab1_backend.service;


import com.arsenyvekshin.lab1_backend.entity.Role;
import com.arsenyvekshin.lab1_backend.entity.User;
import io.minio.*;
import io.minio.errors.*;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.ServiceUnavailableException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@RequiredArgsConstructor
@Service
public class MinIOService {
    private final UserService userService;
    private final MinioClient minioClient;

    @Value("${minIO.bucket}")
    private String bucketName;

    public void createBucketIfNotExists() throws Exception {
        try{
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!isExist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (ConnectException e) {
            throw new ServiceUnavailableException("MinIO unavailable: try again later");
        } catch (Exception e) {
            throw e;
        }

    }

    @Transactional
    public String uploadFile(File file) throws Exception {
        createBucketIfNotExists();

        String key = userService.getCurrentUser().getUsername() + "-" + file.getName();
        try (FileInputStream inputStream = new FileInputStream(file)){

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(key)
                    .stream(inputStream, file.length(), -1)
                    .contentType(".json")
                    .build());

        }
        return key;
    }

    @Transactional
    public String uploadFile(MultipartFile file) throws Exception {
        createBucketIfNotExists();

        String key = userService.getCurrentUser().getUsername() + "-" + file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();

        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(key)
                .stream(inputStream, file.getSize(), -1)
                .contentType(file.getContentType())
                .build());

        return key;
    }
    public InputStream getFile(@NotNull String key) throws Exception {
        createBucketIfNotExists();
        User user = userService.getCurrentUser();
        if (user.getRole() != Role.ADMIN && !key.contains(user.getUsername()))
            throw new AccessDeniedException("У вас нет прав на доступ к этому файлу");
        return minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .object(key)
                .build());
    }
}
