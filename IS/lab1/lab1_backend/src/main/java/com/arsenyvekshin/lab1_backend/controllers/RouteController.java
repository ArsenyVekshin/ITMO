package com.arsenyvekshin.lab1_backend.controllers;


import com.arsenyvekshin.lab1_backend.entities.AuthToken;
import com.arsenyvekshin.lab1_backend.entities.Role;
import com.arsenyvekshin.lab1_backend.entities.Route;
import com.arsenyvekshin.lab1_backend.repositories.AuthTokenRepository;
import com.arsenyvekshin.lab1_backend.repositories.RouteRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.arsenyvekshin.lab1_backend.utils.JsonUtil.gson;
import static com.arsenyvekshin.lab1_backend.utils.JsonUtil.jsonMessage;



@RestController
@CrossOrigin(origins = "*")
public class RouteController {
    @Autowired
    private AuthTokenRepository authTokenRepository;

    @Autowired
    private RouteRepository routeRepository;


    // CRUD запросы
    @GetMapping(value = "/route", produces = "application/json")
    public ResponseEntity<String> getRotesTable( HttpServletRequest request) {
        return ResponseEntity.ok(gson.toJson(routeRepository.findAll()));
    }

    @PostMapping (value = "/route/add", produces = "application/json")
    public ResponseEntity<String> addRote( HttpServletRequest request) {
        AuthToken token = authTokenRepository.findByToken(request.getHeader("auth-token"));

        Gson gson = new GsonBuilder().create();
        Route route = gson.fromJson(request.getHeader("object"), Route.class);
        route.setOwner(token.getUser());
        routeRepository.save(route);
        return ResponseEntity.ok(jsonMessage("New route add - successful"));
    }

    @PostMapping (value = "/route/update", produces = "application/json")
    public ResponseEntity<String> redactRote( HttpServletRequest request) {
        Gson gson = new GsonBuilder().create();
        Route updatedRoute = gson.fromJson(request.getHeader("object"), Route.class);

        Optional<Route> prevObj = routeRepository.findById(updatedRoute.getId());
        if(prevObj.isPresent()) {
            if (!isObjOwner(request, prevObj.get()))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You don't have rights for this operation");
            if (prevObj.get().isReadonly())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This route is read-only");

            prevObj.get().updateBy(updatedRoute);
            routeRepository.save(prevObj.get());
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The original object was not found");

        return ResponseEntity.ok(jsonMessage("Route update - successful"));
    }

    @DeleteMapping(value = "/route", produces = "application/json")
    public ResponseEntity<String> clearPointsTable (HttpServletRequest request) {
        if (request.getHeader("object") != null) { // удалить 1 объект
            Gson gson = new GsonBuilder().create();
            Route updatedRoute = gson.fromJson(request.getHeader("object"), Route.class);

            Optional<Route> prevObj = routeRepository.findById(updatedRoute.getId());
            if(prevObj.isPresent()) {
                if (!isObjOwner(request, prevObj.get()))
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You don't have rights for this operation");
                if (prevObj.get().isReadonly())
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This route is read-only");

                prevObj.get().updateBy(updatedRoute);
                routeRepository.save(prevObj.get());
            } else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The original object was not found");
        }

        AuthToken token = authTokenRepository.findByToken(request.getHeader("auth-token"));
        routeRepository.clearByUserId(token.getUser().getId());
        return ResponseEntity.ok(jsonMessage("Deleting all user objects - successful"));
    }


    // прочие запросы
    @GetMapping (value = "/route/totalrating", produces = "application/json")
    public ResponseEntity<String> getTotalRating (HttpServletRequest request) {
        return ResponseEntity.ok(gson.toJson(routeRepository.calculateTotalRating()));
    }

    @GetMapping (value = "/route/maxto", produces = "application/json")
    public ResponseEntity<String> getMaxTo (HttpServletRequest request) {
        return ResponseEntity.ok(gson.toJson(routeRepository.findMaxTo()));
    }

    @GetMapping (value = "/route/greaterrating", produces = "application/json")
    public ResponseEntity<String> getGreaterRating(@RequestParam int value, HttpServletRequest request) {
        return ResponseEntity.ok(gson.toJson(routeRepository.findRoutesWithGreaterRating(value)));
    }

    @GetMapping (value = "/route/getallrotesby", produces = "application/json")
    public ResponseEntity<String> getAllRotesBy(@RequestParam long loc1, @RequestParam long loc2, HttpServletRequest request) {
        return ResponseEntity.ok(gson.toJson(routeRepository.findAllRotesBy(loc1, loc2)));
    }


    public boolean isObjOwner(HttpServletRequest request, Route route){
        AuthToken token = authTokenRepository.findByToken(request.getHeader("auth-token"));
        if(token.getUser().isAdmin()) return true;
        return token.getUser() == route.getOwner();
    }


}
