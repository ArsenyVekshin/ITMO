package com.arsenyvekshin.lab1_backend.service;

import com.arsenyvekshin.lab1_backend.dto.CoordinatesDto;
import com.arsenyvekshin.lab1_backend.dto.RouteDto;
import com.arsenyvekshin.lab1_backend.dto.SortedObjectListRequest;
import com.arsenyvekshin.lab1_backend.entity.Coordinates;
import com.arsenyvekshin.lab1_backend.entity.Location;
import com.arsenyvekshin.lab1_backend.entity.Route;
import com.arsenyvekshin.lab1_backend.repository.CoordinatesRepository;
import com.arsenyvekshin.lab1_backend.repository.LocationRepository;
import com.arsenyvekshin.lab1_backend.repository.RouteRepository;
import com.arsenyvekshin.lab1_backend.repository.UserRepository;
import com.arsenyvekshin.lab1_backend.utils.Comparators;
import com.arsenyvekshin.lab1_backend.utils.Converter;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CollectionService {
    private final CoordinatesRepository coordinatesRepository;
    private final LocationRepository locationRepository;
    private final RouteRepository routeRepository;
    private final UserRepository userRepository;

    public void createRoute(RouteDto routeDto) throws IOException {
        Route route = new Route();
        routeRepository.save(buildObject(route, routeDto));
    }

    public List<Route> getRoutes() {
        return routeRepository.findAll();
    }

    public List<Route> getSortedRoutes(SortedObjectListRequest request) throws NoSuchFieldException, IllegalArgumentException {
        switch (request.getSign()){
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

    public void updateRoute(RouteDto routeDto) throws IOException {
        Route route = routeRepository.getById(routeDto.getId());
        routeRepository.save(buildObject(route, routeDto));
    }

    public void deleteRoute(Long id) {
        Route route = routeRepository.getById(id);

        if (coordinatesRepository.calcUsageNum(route.getCoordinates().getId()) > 1)
            route.setCoordinates(null);
        else coordinatesRepository.delete(route.getCoordinates());

        if (locationRepository.calcUsageNum(route.getFrom().getId()) > 1)
            route.setFrom(null);
        else locationRepository.delete(route.getFrom());

        if(route.getTo() != null) {
            if (locationRepository.calcUsageNum(route.getTo().getId()) > 1)
                route.setTo(null);
            else locationRepository.delete(route.getTo());
        }

        routeRepository.delete(route);
    }


    private Route buildObject(Route route, RouteDto routeDto) throws IOException {
        route.updateByDto(routeDto);

        if(routeDto.getCoordinates() != null){
            if(routeDto.getCoordinates().getId() != null) {
                route.setCoordinates(coordinatesRepository.getReferenceById(routeDto.getCoordinates().getId()));
            } else {
                Coordinates buff = new Coordinates();
                buff.updateByDto(routeDto.getCoordinates());
                coordinatesRepository.save(buff);
            }
        }

        if(routeDto.getTo() != null){
            if(routeDto.getTo().getId() != null) {
                route.setTo(locationRepository.getReferenceById(routeDto.getTo().getId()));
            } else {
                Location buff = new Location();
                buff.updateByDto(routeDto.getTo());
                locationRepository.save(buff);
            }
        }

        if(routeDto.getFrom() != null){
            if(routeDto.getFrom().getId() != null) {
                route.setFrom(locationRepository.getReferenceById(routeDto.getFrom().getId()));
            } else {
                Location buff = new Location();
                buff.updateByDto(routeDto.getFrom());
                locationRepository.save(buff);
            }
        }

        if(routeDto.getOwner() != null) {
            route.setOwner(userRepository.findByUsername(routeDto.getOwner()));
        }

        return route;
    }

    public void fillDefault(){
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
