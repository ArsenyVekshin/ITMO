package com.arsenyvekshin.lab1_backend.service;

import com.arsenyvekshin.lab1_backend.dto.RouteDto;
import com.arsenyvekshin.lab1_backend.dto.RoutesListDto;
import com.arsenyvekshin.lab1_backend.dto.SortedObjectListRequest;
import com.arsenyvekshin.lab1_backend.entity.*;
import com.arsenyvekshin.lab1_backend.repository.CoordinatesRepository;
import com.arsenyvekshin.lab1_backend.repository.LocationRepository;
import com.arsenyvekshin.lab1_backend.repository.RouteRepository;
import com.arsenyvekshin.lab1_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Setter
@Service
@RequiredArgsConstructor
public class CollectionService {
    private final CoordinatesRepository coordinatesRepository;
    private final LocationRepository locationRepository;
    private final RouteRepository routeRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final FileStorageService fileStorageService;
    private final LogService logService;
    private final MinIOService minIOService;

    private boolean importEnabled = true;

    private void decryptException(Exception e) throws Exception{
        if (e instanceof DataAccessException)
            throw new HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE, "Database is not available: Try again later.");
        else if (e instanceof IllegalArgumentException)
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
        else if (e instanceof IllegalAccessException)
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, e.getMessage());
        else
            throw e;
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void createRoute(RouteDto routeDto) throws Exception {
        try {
            validateDto(routeDto);
            Route route = new Route();
            routeRepository.save(buildObject(route, routeDto));
        }
        catch (Exception e){
            decryptException(e);
        }
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void createRoutesFromFile(MultipartFile file) throws Exception {
        Long operationId = logService.addImportLog(0L);
        String uploadedFileLink = null;
        try {
            RoutesListDto arr = fileStorageService.parseRoutes(file);

            for (RouteDto routeDto : arr.getRoutes()) createRoute(routeDto);

            // Проверка кейса "RuntimeException внутри транзакции"
            if(!importEnabled) throw new RuntimeException("Import from files disabled. Try again later");

            uploadedFileLink = minIOService.uploadFile(file);

            logService.setKeyLink(operationId, uploadedFileLink);
            logService.markImportLogSuccess(operationId, (long) arr.getRoutes().size());

        } catch (Exception e) {
            logService.markImportLogError(operationId, e.getMessage());
            decryptException(e);
        }

    }

    @Transactional(readOnly = true)
    public List<Route> getRoutes() throws Exception {
        try {
            return routeRepository.findAll();
        }
        catch (Exception e){
            decryptException(e);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<Route> getSortedRoutes(SortedObjectListRequest request) throws Exception{
        try {
            return switch (request.getSign()) {
                case '=' -> routeRepository.findAll().stream()
                        .filter(entity -> entity.compareTo(request.getValue(), request.getField()) == 0)
                        .toList();
                case '>' -> routeRepository.findAll().stream()
                        .filter(entity -> entity.compareTo(request.getValue(), request.getField()) > 0)
                        .toList();
                case '<' -> routeRepository.findAll().stream()
                        .filter(entity -> entity.compareTo(request.getValue(), request.getField()) < 0)
                        .toList();
                default -> throw new IllegalArgumentException("Признак сравнения не опознан");
            };
        }
        catch (Exception e){
            decryptException(e);
        }
       return null;
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void updateRoute(RouteDto routeDto) throws Exception {
        try {
            Route route = routeRepository.getById(routeDto.getId());
            User redactor = userService.getCurrentUser();
            if (route.isReadonly()) throw new IllegalArgumentException("Объект помечен как READONLY");
            if (redactor.getRole() == Role.ADMIN || redactor == route.getOwner())
                routeRepository.save(buildObject(route, routeDto));
            else throw new IllegalAccessError("У вас нет прав на редактирование этого объекта");
        }
        catch (Exception e){
            decryptException(e);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteRoute(Long id) throws Exception {
        try {
            Route route = routeRepository.getById(id);
            User redactor = userService.getCurrentUser();
            if (route.isReadonly()) throw new IllegalArgumentException("Объект помечен как READONLY");
            if (redactor.getRole() == Role.ADMIN || redactor == route.getOwner())
                routeRepository.delete(route);
            else throw new IllegalAccessError("У вас нет прав на редактирование этого объекта");
        }
        catch (Exception e){
            decryptException(e);
        }

    }


    private Route buildObject(Route route, RouteDto routeDto) throws Exception {
        route.updateByDto(routeDto);

        if (routeDto.getCoordinates() != null) {
            if (routeDto.getCoordinates().getId() != null) {
                route.setCoordinates(coordinatesRepository.getReferenceById(routeDto.getCoordinates().getId()));
            } else {
                Coordinates buff = new Coordinates();
                buff.updateByDto(routeDto.getCoordinates());
                coordinatesRepository.save(buff);
                route.setCoordinates(buff);
            }
        }

        if (routeDto.getTo() != null) {
            if (routeDto.getTo().getId() != null) {
                route.setTo(locationRepository.getReferenceById(routeDto.getTo().getId()));
            } else {
                Location buff = new Location();
                buff.updateByDto(routeDto.getTo());
                locationRepository.save(buff);
                route.setTo(buff);
            }
        }

        if (routeDto.getFrom() != null) {
            if (routeDto.getFrom().getId() != null) {
                route.setFrom(locationRepository.getReferenceById(routeDto.getFrom().getId()));
            } else {
                Location buff = new Location();
                buff.updateByDto(routeDto.getFrom());
                locationRepository.save(buff);
                route.setFrom(buff);
            }
        }

        if (routeDto.getOwner() != null) {
            route.setOwner(userRepository.findByUsername(routeDto.getOwner()));
        }

        return route;
    }


    private void validateDto(RouteDto dto) throws Exception {
        if (dto.getName() != null) {
            if(!routeRepository.findRouteByName(dto.getName()).isEmpty())
                throw new IllegalArgumentException("Имя маршрута должно быть уникальным");
        }

        if(dto.getFrom() != null){
            if(!locationRepository.findLocationByName(dto.getFrom().getName()).isEmpty())
                throw new IllegalArgumentException("Имя локации FROM должно быть уникальным");
        }

        if(dto.getTo() != null){
            if(!locationRepository.findLocationByName(dto.getTo().getName()).isEmpty())
                throw new IllegalArgumentException("Имя локации TO должно быть уникальным");
        }
    }


}
