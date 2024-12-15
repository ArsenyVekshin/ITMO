package com.arsenyvekshin.lab1_backend.controller;

import com.arsenyvekshin.lab1_backend.dto.MessageInfoDto;
import com.arsenyvekshin.lab1_backend.dto.RouteDto;
import com.arsenyvekshin.lab1_backend.dto.SortedObjectListRequest;
import com.arsenyvekshin.lab1_backend.entity.Route;
import com.arsenyvekshin.lab1_backend.repository.RouteRepository;
import com.arsenyvekshin.lab1_backend.service.CollectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/route")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Управление маршрутами", description = "Методы для взаимодействия коллекцией маршрутов.")
public class CollectionController {
    private final RouteRepository routeRepository;
    private final CollectionService collectionService;

    @Operation(summary = "Список записей в коллекции")
    @GetMapping("/list")
    public List<Route> getRoutesList() {
        return collectionService.getRoutes();
    }

    @Operation(summary = "Список записей в коллекции, после сортировки")
    @GetMapping("/list/sorted")
    public List<Route> getSortedRoutesList(SortedObjectListRequest request) throws NoSuchFieldException {
        return collectionService.getSortedRoutes(request);
    }

    @Operation(summary = "Добавить новый маршрут")
    @PostMapping("/add")
    public MessageInfoDto addRoute(@RequestBody @Valid RouteDto route) throws IOException {
        collectionService.createRoute(route);
        return new MessageInfoDto("ok");
    }

    @Operation(summary = "Добавить набор маршрутов при помощи файла")
    @PostMapping("/add/file")
    public MessageInfoDto multiAddRoute(@RequestParam("file") MultipartFile file) throws Exception {
        collectionService.createRoutesFromFile(file);
        return new MessageInfoDto("ok");
    }

    @Operation(summary = "Изменить существующий маршрут")
    @PostMapping("/update")
    public MessageInfoDto updateRoute(@RequestBody @Valid RouteDto route) throws IOException {
        collectionService.updateRoute(route);
        return new MessageInfoDto("ok");
    }

    @Operation(summary = "Удалить существующий маршрут")
    @PostMapping("/delete")
    public MessageInfoDto deleteRoute(@RequestBody MessageInfoDto dto) {
        collectionService.deleteRoute(Long.valueOf(dto.getMessage()));
        return new MessageInfoDto("ok");
    }

    @Operation(summary = "заполнить тестовыми сущностями")
    @PostMapping("/default")
    public MessageInfoDto deleteRoute() {
        collectionService.fillDefault();
        return new MessageInfoDto("ok");
    }

    @Operation(summary = "Рассчитать сумму значений поля rating для всех объектов.")
    @GetMapping("/func/total-rating")
    public int calculateTotalRating() {
        return routeRepository.calculateTotalRating();
    }

    @Operation(summary = "Вернуть один (любой) объект, значение поля to которого является максимальным.")
    @GetMapping("/func/max-to")
    public List<Route> findMaxTo() {
        return routeRepository.findMaxTo();
    }

    @Operation(summary = "Вернуть массив объектов, значение поля rating которых больше заданного.")
    @GetMapping("/func/greatest-rate")
    public List<Route> findRoutesWithGreaterRating(int rating) {
        return routeRepository.findRoutesWithGreaterRating(rating);
    }

    @Operation(summary = "Найти все маршруты между указанными пользователем локациями.")
    @GetMapping("/func/all-routes-by")
    public List<Route> findAllRotesBy(Long from_location_id, Long to_location_id) {
        return routeRepository.findAllRotesBy(from_location_id, to_location_id);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageInfoDto error(Exception ex) {
        return new MessageInfoDto(ex.getMessage());
    }

}
