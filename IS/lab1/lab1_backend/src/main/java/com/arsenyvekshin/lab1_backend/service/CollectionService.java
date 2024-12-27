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
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CollectionService {
    private static final Object lock = new Object();
    private static boolean importEnabled = true;
    private final CoordinatesRepository coordinatesRepository;
    private final LocationRepository locationRepository;
    private final RouteRepository routeRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final FileStorageService fileStorageService;
    private final LogService logService;
    private final MinIOService minIOService;

    public void setImportEnabled(boolean status) {
        importEnabled = status;
    }

    private void decryptException(Exception e) throws Exception {
        if (e instanceof DataAccessException)
            throw new HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE, "Database is not available: Try again later.");
        else if (e instanceof IllegalArgumentException)
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
        else if (e instanceof IllegalAccessException)
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, e.getMessage());
        else
            throw e;
    }

    @Transactional // propagination = Propagation.REQUIRES_NEW
    public void createRoute(RouteDto routeDto) throws Exception {
        try {
            validateDto(routeDto);
            Route route = new Route();
            routeRepository.save(buildObject(route, routeDto));
        } catch (Exception e) {
            decryptException(e);
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void createRoutesFromFile(MultipartFile file) throws Exception {
        synchronized (lock) {
            Long operationId = logService.addImportLog(0L);
            String uploadedFileLink;
            try {
                RoutesListDto arr = fileStorageService.parseRoutes(file);

                for (RouteDto routeDto : arr.getRoutes()) createRoute(routeDto);

                // Проверка кейса "RuntimeException внутри транзакции"
                if (!importEnabled) throw new RuntimeException("Import from files disabled. Try again later");

                uploadedFileLink = minIOService.uploadFile(file, operationId.toString());

                logService.setKeyLink(operationId, uploadedFileLink);
                logService.markImportLogSuccess(operationId, (long) arr.getRoutes().size());

            } catch (Exception e) {
                logService.setErrorMes(operationId, e.getMessage());
                decryptException(e);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<Route> getRoutes() throws Exception {
        try {
            return routeRepository.findAll();
        } catch (Exception e) {
            decryptException(e);
        }
        return new ArrayList<>();
    }

    @Transactional(readOnly = true)
    public List<Route> getSortedRoutes(SortedObjectListRequest request) {
        switch (request.getSign()) {
            case '=':
                return routeRepository.findAll().stream()
                        .filter(entity -> entity.compareTo(request.getValue(), request.getField()) == 0)
                        .toList();
            case '>':
                return routeRepository.findAll().stream()
                        .filter(entity -> entity.compareTo(request.getValue(), request.getField()) > 0)
                        .toList();
            case '<':
                return routeRepository.findAll().stream()
                        .filter(entity -> entity.compareTo(request.getValue(), request.getField()) < 0)
                        .toList();
            default:
                throw new IllegalArgumentException("Признак сравнения не опознан");
        }
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void updateRoute(RouteDto routeDto) throws Exception {
        Route route = routeRepository.getById(routeDto.getId());
        User redactor = userService.getCurrentUser();
        if (route.isReadonly()) throw new IllegalArgumentException("Обьект помечен как READONLY");
        if (redactor.getRole() == Role.ADMIN || redactor == route.getOwner())
            routeRepository.save(buildObject(route, routeDto));
        else throw new IllegalAccessError("У вас нет прав на редактирование этого объекта");
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void deleteRoute(Long id) {
        Route route = routeRepository.getById(id);
        User redactor = userService.getCurrentUser();
        if (route.isReadonly()) throw new IllegalArgumentException("Обьект помечен как READONLY");
        if (redactor.getRole() == Role.ADMIN || redactor == route.getOwner())
            routeRepository.delete(route);
        else throw new IllegalAccessError("У вас нет прав на редактирование этого объекта");
    }


    private Route buildObject(Route route, RouteDto routeDto) throws IOException {
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


    private void validateDto(RouteDto dto) {
        if (dto.getName() != null) {
            if (!routeRepository.findRouteByName(dto.getName()).isEmpty())
                throw new IllegalArgumentException("Имя маршрута должно быть уникальным");
        }

        if (dto.getFrom() != null) {
            if (!locationRepository.findLocationByName(dto.getFrom().getName()).isEmpty())
                throw new IllegalArgumentException("Имя локации FROM должно быть уникальным");
        }

        if (dto.getTo() != null) {
            if (!locationRepository.findLocationByName(dto.getTo().getName()).isEmpty())
                throw new IllegalArgumentException("Имя локации TO должно быть уникальным");
        }
    }

    public void fillDefault() {
        Coordinates c1 = new Coordinates(0L, 1, 1);
        Coordinates c2 = new Coordinates(0L, 2, 2);
        Coordinates c3 = new Coordinates(0L, 3, 3);
        coordinatesRepository.save(c1);
        coordinatesRepository.save(c2);
        coordinatesRepository.save(c3);

        Location l1 = new Location(0L, 1L, 1L, 1L, "name1");
        Location l2 = new Location(0L, 2L, 2L, 2L, "name2");
        Location l3 = new Location(0L, 3L, 3L, 3L, "name3");
        locationRepository.save(l1);
        locationRepository.save(l2);
        locationRepository.save(l3);


        Route r1 = new Route(0L, "name1", coordinatesRepository.getById(1L), LocalDate.now(), locationRepository.getById(1L), locationRepository.getById(2L), 1.0, 1, userRepository.findByUsername("user1"), false);
        Route r2 = new Route(0L, "name2", coordinatesRepository.getById(2L), LocalDate.now(), locationRepository.getById(2L), locationRepository.getById(3L), 2.0, 2, userRepository.findByUsername("user1"), false);
        Route r3 = new Route(0L, "name2", coordinatesRepository.getById(2L), LocalDate.now(), locationRepository.getById(3L), locationRepository.getById(1L), 3.0, 3, userRepository.findByUsername("user1"), false);
        routeRepository.save(r1);
        routeRepository.save(r2);
        routeRepository.save(r3);
    }

}
