package com.arsenyvekshin.backend.controllers;




import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.arsenyvekshin.backend.utils.JsonUtil.*;
import com.arsenyvekshin.backend.db.AuthTokenRepository;
import com.arsenyvekshin.backend.db.PointRepository;
import com.arsenyvekshin.backend.entities.AuthToken;
import com.arsenyvekshin.backend.entities.Point;
import com.arsenyvekshin.backend.exceptions.OutOfBoundException;

@RestController
public class GraphController {
    @Autowired
    private AuthTokenRepository authTokenRepository;

    @Autowired
    private PointRepository pointRepository;

    @GetMapping(value = "/point", produces = "application/json")
    public ResponseEntity<String> getPointsTable(HttpServletRequest request) {
        AuthToken token = authTokenRepository.findByToken(request.getHeader("auth-token"));

        if (token == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jsonMessage("wrong auth token"));
        if (token.isExpired())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jsonMessage("auth token expired"));

        return ResponseEntity.ok(gson.toJson(pointRepository.findByUserId(token.getUser().getId())));
    }

    @PostMapping (value = "/point", produces = "application/json")
    public ResponseEntity<String> addPoint( @RequestParam double x,
                                            @RequestParam double y,
                                            @RequestParam double r,
                                            HttpServletRequest request) {

        AuthToken token = authTokenRepository.findByToken(request.getHeader("auth-token"));

        if (token == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jsonMessage("wrong auth token"));
        if (token.isExpired())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jsonMessage("auth token expired"));

        try {
            Point point = new Point(x, y, r, token.getUser());
            pointRepository.save(point);
            return ResponseEntity.ok(gson.toJson(point));
        } catch (OutOfBoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonMessage("entered point incorrect"));
        }

    }

    @DeleteMapping(value = "/point", produces = "application/json")
    public ResponseEntity<String> clearPointsTable(HttpServletRequest request) {
        AuthToken token = authTokenRepository.findByToken(request.getHeader("auth-token"));

        if (token == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jsonMessage("wrong auth token"));
        if (token.isExpired())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jsonMessage("auth token expired"));

        pointRepository.clearByUserId(token.getUser().getId());
        return ResponseEntity.ok(jsonMessage("User points clear - successful"));
    }

}
