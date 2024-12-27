package com.arsenyvekshin.lab1_backend.configuration;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinIOConfiguration {

    @Value("${minIO.url}")
    String url;

    @Value("${minIO.access-key}")
    String access_key;

    @Value("${minIO.secret-key}")
    String secret_key;


    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(url)
                .credentials(access_key, secret_key)
                .build();
    }

}
