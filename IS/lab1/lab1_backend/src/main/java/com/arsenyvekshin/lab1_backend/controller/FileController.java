package com.arsenyvekshin.lab1_backend.controller;

import com.arsenyvekshin.lab1_backend.dto.MessageInfoDto;
import com.arsenyvekshin.lab1_backend.service.MinIOService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Работа с файлами", description = "Методы для взаимодействия с файлами.")
public class FileController {

    private final MinIOService minIOService;

    @Operation(summary = "Отправить файл на сервер")
    @PostMapping("/upload")
    public MessageInfoDto uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        minIOService.uploadFile(file);
        return new MessageInfoDto("ok");

    }

    @Operation(summary = "Получить файл с сервера по ключу")
    @GetMapping("/get/{key}")
    public InputStreamResource downloadFile(@PathVariable String key) throws Exception {
        return new InputStreamResource(minIOService.getFile(key));
    }
}
