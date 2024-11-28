package com.arsenyvekshin.lab1_backend.service;

import com.arsenyvekshin.lab1_backend.dto.RoutesListDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileStorageService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${file.upload-dir}")
    String uploadDir;

    public File storeFile(MultipartFile file) throws IOException {

        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }
        String filePath = uploadDir + File.separator + file.getOriginalFilename();
        File dest = new File(filePath);
        file.transferTo(dest);
        return dest;
    }

    public void deleteFile(File file) throws IOException {
        boolean a = file.delete();
    }

    public RoutesListDto parseRoutes(File file) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(file, RoutesListDto.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error parsing the file");
        }
    }

}
