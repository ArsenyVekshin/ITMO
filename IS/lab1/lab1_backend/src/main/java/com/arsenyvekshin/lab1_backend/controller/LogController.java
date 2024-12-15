package com.arsenyvekshin.lab1_backend.controller;

import com.arsenyvekshin.lab1_backend.dto.ImportLogDto;
import com.arsenyvekshin.lab1_backend.service.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/log")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Логирование", description = "Методы для взаимодействия с лоцированием.")
public class LogController {
    private final LogService logService;

    @Operation(summary = "Лог импорта из файлов")
    @GetMapping("/import")
    public List<ImportLogDto> getRoutesList() {
        return logService.getImportLog();
    }

}
