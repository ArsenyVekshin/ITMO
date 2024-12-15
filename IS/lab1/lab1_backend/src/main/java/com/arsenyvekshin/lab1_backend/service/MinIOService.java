package com.arsenyvekshin.lab1_backend.service;


import com.arsenyvekshin.lab1_backend.entity.Role;
import com.arsenyvekshin.lab1_backend.entity.User;
import io.minio.*;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;


@RequiredArgsConstructor
@Service
public class MinIOService {
    private final UserService userService;
    private final MinioClient minioClient;

    @Value("${minIO.bucket}")
    private String bucketName;

    public void createBucketIfNotExists() throws Exception {
        boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!isExist) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
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
        User user = userService.getCurrentUser();
        if (user.getRole() != Role.ADMIN && !key.contains(user.getUsername()))
            throw new AccessDeniedException("У вас нет прав на доступ к этому файлу");
        return minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .object(key)
                .build());
    }
}
